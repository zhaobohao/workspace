package com.gitee.sop.servercommon.param;

import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.servercommon.annotation.ApiAbility;
import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.servercommon.bean.OpenContext;
import com.gitee.sop.servercommon.bean.OpenContextImpl;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.util.OpenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.Principal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析request参数中的业务参数，绑定到方法参数上
 *
 * @author tanghc
 */
@Slf4j
public class ApiArgumentResolver implements SopHandlerMethodArgumentResolver {

    private Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache = new ConcurrentHashMap<>(256);

    private ParamValidator paramValidator = new ServiceParamValidator();

    private static Class<?> pushBuilder;

    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private static List<MethodParameter> openApiParams = new ArrayList<>(64);

    static {
        try {
            pushBuilder = ClassUtils.forName("javax.servlet.http.PushBuilder",
                    ServletRequestMethodArgumentResolver.class.getClassLoader());
        } catch (ClassNotFoundException ex) {
            // Servlet 4.0 PushBuilder not found - not supported for injection
            pushBuilder = null;
        }
    }

    @Override
    public void setRequestMappingHandlerAdapter(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        List<HandlerMethodArgumentResolver> argumentResolversNew = new ArrayList<>(64);
        // 先加自己，确保在第一个位置
        argumentResolversNew.add(this);
        argumentResolversNew.addAll(requestMappingHandlerAdapter.getArgumentResolvers());
        requestMappingHandlerAdapter.setArgumentResolvers(argumentResolversNew);
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 是否有注解
        boolean hasAnnotation = methodParameter.hasMethodAnnotation(ApiMapping.class)
                || methodParameter.hasMethodAnnotation(ApiAbility.class);
        if (hasAnnotation) {
            openApiParams.add(methodParameter);
        }
        Class<?> paramType = methodParameter.getParameterType();
        if (paramType == OpenContext.class) {
            return true;
        }
        // 排除的
        boolean exclude = (
            WebRequest.class.isAssignableFrom(paramType) ||
            ServletRequest.class.isAssignableFrom(paramType) ||
            MultipartRequest.class.isAssignableFrom(paramType) ||
            HttpSession.class.isAssignableFrom(paramType) ||
            (pushBuilder != null && pushBuilder.isAssignableFrom(paramType)) ||
            Principal.class.isAssignableFrom(paramType) ||
            InputStream.class.isAssignableFrom(paramType) ||
            Reader.class.isAssignableFrom(paramType) ||
            HttpMethod.class == paramType ||
            Locale.class == paramType ||
            TimeZone.class == paramType ||
            ZoneId.class == paramType ||
            ServletResponse.class.isAssignableFrom(paramType) ||
            OutputStream.class.isAssignableFrom(paramType) ||
            Writer.class.isAssignableFrom(paramType)
        );
        // 除此之外都匹配
        return !exclude;
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter
            , ModelAndViewContainer modelAndViewContainer
            , NativeWebRequest nativeWebRequest
            , WebDataBinderFactory webDataBinderFactory
    ) throws Exception {
        if (openApiParams.contains(methodParameter)) {
            Object paramObj = this.getParamObject(methodParameter, nativeWebRequest);
            if (paramObj != null) {
                // JSR-303验证
                paramValidator.validateBizParam(paramObj);
                return paramObj;
            }
        }
        HandlerMethodArgumentResolver resolver = getOtherArgumentResolver(methodParameter);
        if (resolver != null) {
            return resolver.resolveArgument(
                    methodParameter
                    , modelAndViewContainer
                    , nativeWebRequest
                    , webDataBinderFactory
            );
        }
        return null;
    }


    /**
     * 获取参数对象，将request中的参数绑定到实体对象中去
     *
     * @param methodParameter  方法参数
     * @param nativeWebRequest request
     * @return 返回参数绑定的对象，没有返回null
     */
    protected Object getParamObject(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        JSONObject requestParams = OpenUtil.getRequestParams(request);
        // 方法参数类型
        Class<?> parameterType = methodParameter.getParameterType();
        boolean isOpenContextParam = parameterType == OpenContext.class;
        Class<?> bizObjClass = parameterType;
        // 参数是OpenRequest，则取OpenRequest的泛型参数类型
        if (isOpenContextParam) {
            bizObjClass = this.getOpenRequestGenericParameterClass(methodParameter);
        }
        OpenContext openContext = new OpenContextImpl(requestParams, bizObjClass);
        ServiceContext.getCurrentContext().setOpenContext(openContext);
        Object bizObj = openContext.getBizObject();
        this.bindUploadFile(bizObj, request);
        return isOpenContextParam ? openContext : bizObj;
    }

    /**
     * 获取泛型参数类型
     *
     * @param methodParameter 参数
     * @return 返回泛型参数class
     */
    protected Class<?> getOpenRequestGenericParameterClass(MethodParameter methodParameter) {
        Type genericParameterType = methodParameter.getGenericParameterType();
        Class<?> bizObjClass = null;
        if (genericParameterType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genericParameterType).getActualTypeArguments();
            if (params != null && params.length >= 1) {
                bizObjClass = (Class<?>) params[0];
            }
        }
        return bizObjClass;
    }

    /**
     * 将上传文件对象绑定到属性中
     *
     * @param bizObj             业务参数
     * @param httpServletRequest
     */
    protected void bindUploadFile(Object bizObj, HttpServletRequest httpServletRequest) {
        if (bizObj == null) {
            return;
        }
        if (this.isMultipartRequest(httpServletRequest)) {
            MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
            Class<?> bizClass = bizObj.getClass();
            ReflectionUtils.doWithFields(bizClass, field -> {
                ReflectionUtils.makeAccessible(field);
                String name = field.getName();
                MultipartFile multipartFile = request.getFile(name);
                ReflectionUtils.setField(field, bizObj, multipartFile);
            }, field -> field.getType() == MultipartFile.class);
        }
    }

    protected boolean isMultipartRequest(HttpServletRequest request) {
        return request instanceof MultipartRequest;
    }

    /**
     * 获取其它的参数解析器
     *
     * @param parameter 业务参数
     * @return 返回合适参数解析器，没有返回null
     */
    protected HandlerMethodArgumentResolver getOtherArgumentResolver(MethodParameter parameter) {
        HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
        if (result == null) {
            List<HandlerMethodArgumentResolver> argumentResolvers = this.requestMappingHandlerAdapter.getArgumentResolvers();
            for (HandlerMethodArgumentResolver methodArgumentResolver : argumentResolvers) {
                if (methodArgumentResolver instanceof SopHandlerMethodArgumentResolver) {
                    continue;
                }
                if (methodArgumentResolver.supportsParameter(parameter)) {
                    result = methodArgumentResolver;
                    this.argumentResolverCache.put(parameter, result);
                    break;
                }
            }
        }
        return result;
    }

    public void setParamValidator(ParamValidator paramValidator) {
        this.paramValidator = paramValidator;
    }
}