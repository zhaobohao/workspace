package com.gitee.easyopen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.gitee.easyopen.bean.ApiDefinition;
import com.gitee.easyopen.bean.ApiInvocation;
import com.gitee.easyopen.bean.Callers;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.bean.DefinitionHolder;
import com.gitee.easyopen.bean.MethodCaller;
import com.gitee.easyopen.doc.ApiDocHolder;
import com.gitee.easyopen.doc.ApiDocItem;
import com.gitee.easyopen.exception.BusinessParamException;
import com.gitee.easyopen.interceptor.ApiInterceptor;
import com.gitee.easyopen.message.Errors;
import com.gitee.easyopen.register.SingleParameterContext;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.easyopen.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理客户端请求分发
 * 
 * @author tanghc
 *
 */
public class ApiInvoker implements Invoker {

    private static final Logger logger = LoggerFactory.getLogger(ApiInvoker.class);
    private static final Logger param_log = LoggerFactory.getLogger(BusinessParamException.class);

    private static final ApiInterceptor[] EMPTY_INTERCEPTOR_ARRAY = {};

    private static final EmptyObject EMPTY_OBJECT = new EmptyObject();
    

    @Override
    public Object invoke(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        ApiContext.setRequest(request);
        ApiContext.setResponse(response);
        try {
            // 解析参数
            ApiParam param = ApiContext.getApiConfig().getParamParser().parse(request);
            ApiContext.setApiParam(param);
            return this.doInvoke(param,request,response);
        } catch (Throwable e) {
            if(e instanceof InvocationTargetException) {
                e = ((InvocationTargetException)e).getTargetException();
            }
            this.logError(e);
            throw e;
        }
    }

    @Override
    public Object invokeMock(HttpServletRequest request, HttpServletResponse response) {
        ApiContext.setRequest(request);
        ApiContext.setResponse(response);
        // 解析参数
        ApiParam param = ApiContext.getApiConfig().getParamParser().parse(request);
        ApiContext.setApiParam(param);

        ApiDocItem apiDocItem = ApiDocHolder.getApiDocBuilder().getApiDocItem(param.fatchName(), param.fatchVersion());
        if (apiDocItem == null) {
            return EMPTY_OBJECT;
        }
        Object resultData = apiDocItem.fatchResultData();
        if (resultData == null) {
            resultData = EMPTY_OBJECT;
        }
        return resultData;
    }

    protected void logError(Throwable e) {
        if (e instanceof BusinessParamException) {
            BusinessParamException ex = (BusinessParamException)e;
            param_log.warn("业务参数错误,code:{}, msg:{}", ex.getCode(), ex.getMessage());
        } else {
            logger.error(e.getMessage(), e);
        }
    }
    
    protected void initJwtInfo(HttpServletRequest request,ApiParam param) {
        Map<String, Claim> data = null;
        String jwt = this.getHeader(request, Consts.AUTHORIZATION);
        if(jwt != null && jwt.startsWith(Consts.PREFIX_BEARER)) {
            jwt = jwt.replace(Consts.PREFIX_BEARER, "");
            data = ApiContext.getApiConfig().getJwtService().verfiyJWT(jwt);
        }
        ApiContext.setJwtData(data);
    }
    
    protected String getHeader(HttpServletRequest request, String key) {
        String value = request.getHeader(key);
        if(value == null) {
            return null;
        }
        if(ApiContext.isEncryptMode()) {
            value = ApiContext.decryptAES(value);
        } else if(ApiContext.hasUseNewSSL(request)) {
            value = ApiContext.decryptAESFromBase64String(value);
        }
        return value;
    }
    
    
    /**
     * 调用具体的业务方法，返回业务结果
     * @param param
     * @param request
     * @param response
     * @return 返回最终结果
     * @throws Throwable
     */
    protected Object doInvoke(ApiParam param,HttpServletRequest request, HttpServletResponse response) throws Throwable {
        ApiDefinition apiDefinition = this.getApiDefinition(param);
        ApiContext.setApiMeta(apiDefinition);
        if (!apiDefinition.isIgnoreJWT()) {
            this.initJwtInfo(request, param);
        }
        // 方法参数
        Object methodArgu = null;
        // 返回结果
        Object invokeResult = null;

        Validator validator = ApiContext.getApiConfig().getValidator();

        param.setIgnoreSign(apiDefinition.isIgnoreSign());
        param.setIgnoreValidate(apiDefinition.isIgnoreValidate());
        // 验证操作，这里有负责验证签名参数
        validator.validate(param);

        // 业务参数json格式
        String busiJsonData = ApiContext.getApiConfig().getDataDecoder().decode(param);

        // 业务参数Class
        Class<?> arguClass = apiDefinition.getMethodArguClass();

        boolean isSingleParameter = apiDefinition.isSingleParameter();
        Object singleParamProxy = null;

        int interceptorIndex = 0;
        try {
            // 将参数绑定到业务方法参数上，业务方法参数可以定义的类型：JSONObject,Map<String,Object>,String,业务参数类
            if (arguClass != null) {
                if(arguClass == JSONObject.class) {
                    methodArgu = JSON.parseObject(busiJsonData);
                } else if(arguClass == Map.class) {
                    methodArgu = new HashMap<String,Object>(JSON.parseObject(busiJsonData));
                } else if(isSingleParameter) {
                    SingleParameterContext.SingleParameterContextValue value = SingleParameterContext.get(apiDefinition.getMethod());
                    if (value != null) {
                        JSONObject jsonObj = JSON.parseObject(busiJsonData);
                        methodArgu = jsonObj.getObject(value.getParamName(), arguClass);
                        singleParamProxy = jsonObj.toJavaObject(value.getWrapClass());
                    }
                } else {
                    methodArgu = JSON.parseObject(busiJsonData, arguClass);
                }
                this.bindUploadFile(methodArgu);
            }
            // 拦截器
            ApiInterceptor[] interceptors = ApiContext.getApiConfig().getInterceptors();
            if(interceptors == null) {
                interceptors = EMPTY_INTERCEPTOR_ARRAY;
            }
        
            //1. 调用preHandle
            for (int i = 0; i < interceptors.length; i++) {
                ApiInterceptor interceptor = interceptors[i];  
                if (interceptor.match(apiDefinition) && !interceptor.preHandle(request, response, apiDefinition.getHandler(),methodArgu)) {
                    //1.1、失败时触发afterCompletion的调用  
                    triggerAfterCompletion(apiDefinition, interceptorIndex, request, response, methodArgu, null,null);
                    return null;  
                }
                //1.2、记录当前预处理成功的索引
                interceptorIndex = i;
            }

            // 验证业务参数JSR-303
            this.validateBizArgu(validator, methodArgu, singleParamProxy);

            /* *** 调用业务方法,被@Api标记的方法 ***/
            MethodCaller methodCaller = apiDefinition.getMethodCaller();
            if (methodCaller != null) {
                invokeResult = methodCaller.call(new ApiInvocation(apiDefinition, methodArgu));
            } else {
                invokeResult = Callers.call(apiDefinition, methodArgu);
            }

            //3、调用postHandle,业务方法调用后处理（逆序）  
            for (int i = interceptors.length - 1; i >= 0; i--) {  
                ApiInterceptor interceptor = interceptors[i];  
                if(interceptor.match(apiDefinition)) {
                    interceptor.postHandle(request, response, apiDefinition.getHandler(), methodArgu, invokeResult);
                }
            }  
            
            if(invokeResult == null) {
                invokeResult = EMPTY_OBJECT;
            }

            // 最终返回的对象
            Object finalReturn = this.wrapResult(apiDefinition, invokeResult);

            //4、触发整个请求处理完毕回调方法afterCompletion  
            triggerAfterCompletion(apiDefinition, interceptorIndex, request, response, methodArgu, finalReturn, null);
            
            return finalReturn;
        } catch (Exception e) {
            this.triggerAfterCompletion(apiDefinition, interceptorIndex, request, response, methodArgu, invokeResult, e);
            throw e;
        }
    }

    protected ApiDefinition getApiDefinition(ApiParam param) {
        ApiDefinition apiDefinition = DefinitionHolder.getByParam(param);
        if (apiDefinition == null) {
            throw Errors.NO_API.getException(param.fatchName(), param.fatchVersion());
        }
        return apiDefinition;
    }

    /** 包装方法返回的结果 */
    protected Object wrapResult(ApiDefinition apiDefinition, Object invokeResult) {
        // 最终返回的对象
        Object finalReturn = invokeResult;
        if (apiDefinition.noReturn()) {
            finalReturn = null;
        } else if(apiDefinition.isWrapResult()) {
            // 对返回结果包装
            finalReturn = ApiContext.getApiConfig().getResultCreator().createResult(invokeResult);
        }
        return finalReturn;
    }

    /** triggerAfterCompletion方法 */
    private void triggerAfterCompletion (ApiDefinition definition,int interceptorIndex,  
                HttpServletRequest request, HttpServletResponse response, Object argu,Object result, Exception e) throws Exception {  
        // 5、触发整个请求处理完毕回调方法afterCompletion （逆序从1.2中的预处理成功的索引处的拦截器执行）  
        ApiInterceptor[] interceptors = ApiContext.getApiConfig().getInterceptors();  
        
        if(interceptors != null && interceptors.length > 0) {
            for (int i = interceptorIndex; i >= 0; i--) {  
                ApiInterceptor interceptor = interceptors[i];
                if(interceptor.match(definition)) {
                    interceptor.afterCompletion(request, response, definition.getHandler(),argu,result, e);
                }
            }  
        }
    }

    /**
     * 校验业务参数
     * @param validator 校验器
     * @param methodArgu 方法参数值
     * @param singleParamProxy 单值参数代理对象
     */
    protected void validateBizArgu(Validator validator, Object methodArgu, Object singleParamProxy) {
        if (singleParamProxy != null) {
            validator.validateBusiParam(singleParamProxy);
        }else {
            validator.validateBusiParam(methodArgu);
        }
    }

    /**
     * 绑定上传文件到参数类当中
     * @param methodArgu
     */
    protected void bindUploadFile(Object methodArgu) {
        if(methodArgu != null) {
            UploadContext uploadContext = ApiContext.getUploadContext();
            if(uploadContext != null) {
                List<MultipartFile> files = uploadContext.getAllFile();
                if(files != null && files.size() > 0) {
                    Map<String,Object> filesMap = new HashMap<>(files.size());
                    
                    for (MultipartFile file : files) {
                        filesMap.put(file.getName(), file);
                    }
                    
                    CopyUtil.copyProperties(filesMap, methodArgu);
                    
                    Field field = ReflectionUtil.getListFieldWithGeneric(methodArgu, MultipartFile.class);
                    if(field != null) {
                        ReflectionUtil.invokeFieldValue(methodArgu, field.getName(), files);
                    }
                }
            }
        }
    }
    
    
    private static class EmptyObject implements Serializable {
        private static final long serialVersionUID = 1713263598232463135L;
    }

}
