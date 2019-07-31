package com.gitee.easyopen.doc;

import com.gitee.easyopen.bean.Api;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.util.ReflectionUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 负责生成文档
 *
 * @param <ServiceAnnotation> 类上面的注解，如Controller,ApiService
 * @param <MethodAnnotation>  方法上面的注解，如Api,RequestMapping
 * @author tanghc
 */
public abstract class AbstractApiDocCreator<ServiceAnnotation extends Annotation, MethodAnnotation extends Annotation> {

    /**
     * 默认版本号
     */
    private String defaultVersion;
    private ApplicationContext applicationContext;


    public AbstractApiDocCreator(String defaultVersion, ApplicationContext applicationContext) {
        if (defaultVersion == null) {
            defaultVersion = "";
        }
        this.defaultVersion = defaultVersion;
        this.applicationContext = applicationContext;
    }

    /**
     * 返回service类上面的注解，如Service
     *
     * @return 返回注解class
     */
    protected abstract Class<ServiceAnnotation> getServiceAnnotationClass();

    /**
     * 返回方法上面的注解，如：RequestMapping
     *
     * @return 返回注解class
     */
    protected abstract Class<MethodAnnotation> getMethodAnnotationClass();

    /**
     * 根据方法注解获取api信息
     *
     * @param annotation 方法注解
     * @return 返回api信息
     */
    protected abstract Api getApi(MethodAnnotation annotation);

    public void create() {
        Assert.notNull(applicationContext, "ApplicationContext不能为空");

        String[] beans = ReflectionUtil.findBeanNamesByAnnotationClass(applicationContext, getServiceAnnotationClass());
        ApiDocBuilder apiDocBuilder = ApiDocHolder.createBuilder();
        for (String beanName : beans) {
            Object handler = applicationContext.getBean(beanName);
            Class<?> beanClass = handler.getClass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                // 找方法上面的注解,如@Api,@RequestMapping
                MethodAnnotation methodAnnotation = AnnotationUtils.findAnnotation(method, getMethodAnnotationClass());
                ApiDocMethod apiDocMethod = AnnotationUtils.findAnnotation(method, ApiDocMethod.class);
                // 如果找到
                if (!method.isSynthetic() && methodAnnotation != null && apiDocMethod != null) {
                    final Api api = getApi(methodAnnotation);
                    if (api.getVersion() == null || "".equals(api.getVersion().trim())) {
                        api.setVersion(defaultVersion);
                    }
                    // 生成doc内容
                    apiDocBuilder.addDocItem(api, handler, method);
                }
            }
        }
    }
}
