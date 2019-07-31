package com.gitee.easyopen.register;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.bean.ApiDefinition;
import com.gitee.easyopen.bean.DefinitionHolder;
import com.gitee.easyopen.doc.ApiDocBuilder;
import com.gitee.easyopen.doc.ApiDocHolder;
import com.gitee.easyopen.doc.ApiServiceDocCreator;
import com.gitee.easyopen.doc.DocFileCreator;
import com.gitee.easyopen.doc.MarkdownDocCreator;
import com.gitee.easyopen.exception.DuplicateApiNameException;
import com.gitee.easyopen.message.ErrorFactory;
import com.gitee.easyopen.util.FieldUtil;
import com.gitee.easyopen.util.ReflectionUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.util.ReflectionUtils.MethodFilter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * api注册类,在spring启动完成时进行注册.<br>
 * 
 * <pre>
 * <code>
 * 原理:
 * 1. 在spring容器中找到被@ApiService注解的类
 * 2. 在类中找到被@Api注解的方法
 * 3. 保存方法信息以及类对象,方便后期进行invoke
 * </code>
 * </pre>
 * 
 * @author tanghc
 *
 */
public class ApiRegister {
    private static final Logger logger = LoggerFactory.getLogger(ApiRegister.class);

    private static final ApiMethodFilter API_METHOD_FILTER = new ApiMethodFilter();
    
    private int apiCount;

    private ApiConfig apiConfig;
    private ApplicationContext applicationContext;

    public ApiRegister(ApiConfig apiConfig, ApplicationContext applicationContext) {
        this.apiConfig = apiConfig;
        this.applicationContext = applicationContext;
    }

    /**
     * 接口注册操作
     * @param registCallback 成功后回调
     */
    public void regist(RegistCallback registCallback) {
        logger.info("******** 开始注册操作 ********");
        this.initMessage();
        this.registApi();
        this.createDoc();
        this.afterRegist(registCallback);
        logger.info("******** 注册操作结束 ********");
    }

    /**
     * 初始化国际化消息
     */
    private void initMessage() {
        ErrorFactory.initMessageSource(this.apiConfig.getIsvModules());
    }

    /**
     * 注册接口
     */
    private void registApi() {
        logger.info("开始注册Api接口...");
        ApplicationContext ctx = this.getApplicationContext();
        Assert.notNull(ctx, "ApplicationContext不能为空");
        Assert.notNull(apiConfig, "ApiConfig不能为空");
        
        DefinitionHolder.clear();
        // 找到所有ApiService的类名
        String[] beans = ReflectionUtil.findApiServiceNames(ctx);

        for (String beanName : beans) {
            // 被@ApiService标记的类
            Object handler = ctx.getBean(beanName);
            // 处理beanClass类中被@Api标记的方法
            doWithMethods(handler.getClass(), new ApiMethodProcessor(handler), API_METHOD_FILTER);
        }
        logger.info("注册Api接口完毕，共{}个接口", apiCount);
    }

    /**
     * 注册完成后回调
     * @param registCallback
     */
    private void afterRegist(RegistCallback registCallback) {
        if(registCallback != null) {
            logger.info("执行Api注册回调");
            registCallback.onRegistFinished(apiConfig);
            logger.info("执行Api注册回调完毕");
        }
    }

    /**
     *  改造ReflectionUtils.doWithMethods().
     *  BUG描述：如果Service类有@Transactional注解（作用在类上或方法上），那么spring会使用cglib动态
     *  生成一个子类并且继承原有的类public ClassCGLIB extends Service {}，然后重写
     *  Service的方法，实现动态代理。这时使用ReflectionUtils.doWithMethods()获取方法会拿到所有的method,
     *  其实只应该拿到cglib类中的方法即可。
     * @param clazz
     * @param mc
     * @param mf
     */
    private static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf) {
        // Keep backing up the inheritance hierarchy.
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (mf != null && !mf.matches(method)) {
                continue;
            }
            try {
                mc.doWith(method);
            } catch (IllegalAccessException ex) {
                logger.error("注册API失败", ex);
                System.exit(0);
            }
        }
        // ！！注意：下面这两句【必须注释掉】，如果clazz对象被CGLIB代理，那么父类就是原生类。因为CGLIB是以子类的方式生成。
        // 这里只需要获取子类的方法即可。
//        if (clazz.getSuperclass() != null) {
//            doWithMethods(clazz.getSuperclass(), mc, mf);
//        }
//        else if (clazz.isInterface()) {
//            for (Class<?> superIfc : clazz.getInterfaces()) {
//                doWithMethods(superIfc, mc, mf);
//            }
//        }
    }

    private class ApiMethodProcessor implements ReflectionUtils.MethodCallback {
        private Object handler;
        private ApiService apiServiceAnno;

        public ApiMethodProcessor(Object handler) {
            super();
            this.handler = handler;
            this.apiServiceAnno = AnnotationUtils.findAnnotation(handler.getClass(), ApiService.class);
        }

        @Override
        public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
            checkTransactionalAnnotation(method);
            ReflectionUtils.makeAccessible(method);
            Api api = AnnotationUtils.findAnnotation(method, Api.class);
            boolean ignoreSign = api.ignoreSign() ? true : this.apiServiceAnno.ignoreSign();
            boolean ignoreValidate = api.ignoreValidate() ? true : this.apiServiceAnno.ignoreValidate();
            
            boolean isWrapResult = this.apiServiceAnno.wrapResult() ? api.wrapResult() : false;

            ApiDefinition apiDefinition = new ApiDefinition();
            apiDefinition.setIgnoreSign(ignoreSign);
            apiDefinition.setIgnoreValidate(ignoreValidate);
            apiDefinition.setWrapResult(isWrapResult);
            apiDefinition.setHandler(handler);
            apiDefinition.setMethod(method);
            apiDefinition.setName(api.name());
            apiDefinition.setNoReturn(api.noReturn());
            apiDefinition.setIgnoreJWT(api.ignoreJWT());
            apiDefinition.setIgnoreToken(api.isIgnoreToken());
            String version = api.version();
            if("".equals(version.trim())) {
                version = apiConfig.getDefaultVersion();
            }
            apiDefinition.setVersion(version);

            Parameter[] parameters = method.getParameters();
            Class<?> paramClass = null;
            if (parameters != null && parameters.length > 0) {
                Parameter parameter = parameters[0];
                paramClass = parameter.getType();
                boolean isNumberOrStringType = FieldUtil.isNumberOrStringType(paramClass);
                apiDefinition.setSingleParameter(isNumberOrStringType);
                apiDefinition.setMethodArguClass(paramClass);
                if (isNumberOrStringType) {
                    SingleParameterContext.add(handler, method, parameter);
                }
            }

            logger.debug("注册接口name={},version={},method={} {}({})", api.name(), api.version(),
                    method.getReturnType().getName(), method.getName(), paramClass == null ? "" : paramClass.getName());
            
            try {
                DefinitionHolder.addApiDefinition(apiDefinition);
                apiConfig.getApiRegistEvent().onSuccess(apiDefinition);
            } catch (DuplicateApiNameException e) {
                logger.error(e.getMessage(), e);
                System.exit(0);
            }

            apiCount++;
        }
        
    }

    private void checkTransactionalAnnotation(Method method) throws IllegalAccessException {
        Transactional annotation = AnnotationUtils.findAnnotation(method, Transactional.class);
        // 如果有Transactional注解，方法必须是public
        if (annotation != null && !Modifier.isPublic(method.getModifiers())) {
            throw new IllegalAccessException("方法" + method + "必须申明为public");
        }
    }

    /** 过滤出被@Api标记的方法 */
    private static class ApiMethodFilter implements ReflectionUtils.MethodFilter {
        @Override
        public boolean matches(Method method) {
            return !method.isSynthetic() && AnnotationUtils.findAnnotation(method, Api.class) != null;
        }
    }

    /**
     * 生成doc文档
     */
    private void createDoc() {
        if(this.apiConfig.isShowDoc()) {
            logger.info("生成接口文档");
            new ApiServiceDocCreator(this.apiConfig.getDefaultVersion(), this.applicationContext).create();
        }
        String markdownDocDir = apiConfig.getMarkdownDocDir();
        DocFileCreator docFileCreator = apiConfig.getDocFileCreator();
        if (docFileCreator == null && StringUtils.isNotBlank(markdownDocDir)) {
            docFileCreator = new MarkdownDocCreator(markdownDocDir);
        }
        if (docFileCreator != null) {
            try {
                ApiDocBuilder apiDocBuilder = ApiDocHolder.getApiDocBuilder();
                docFileCreator.createMarkdownDoc(apiDocBuilder.getApiModules());
            } catch (IOException e) {
                logger.error("生成文档文件出错", e);
            }
        }
    }

    public ApiConfig getApiConfig() {
        return apiConfig;
    }

    public void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
