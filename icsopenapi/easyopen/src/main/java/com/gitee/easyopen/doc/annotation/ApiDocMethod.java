package com.gitee.easyopen.doc.annotation;

import com.gitee.easyopen.Result;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 文档方法，作用在方法上
 * @author tanghc
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ApiDocMethod {
    /**
     * @return 描述，接口简单描述，如：用户登录，发送验证码。支持html标签
     */
    String description() default "";
    
    /**
     * @return 备注，可写更详细的介绍，支持html标签
     */
    String remark() default "";

    /**
     * @return 自定义参数
     */
    ApiDocField[] params() default {};

    /**
     * @return 指定参数类型
     */
    Class<?> paramClass() default Object.class;

    /**
     * @return 自定义返回参数
     */
    ApiDocField[] results() default {};

    /**
     * @return 指定返回参数
     */
    Class<?> resultClass() default Object.class;
    
    /**
     * @return 数组元素class类型
     */
    Class<?> elementClass() default Object.class;
    
    /**
     * @return 指定模块下文档显示顺序，值越小越靠前
     */
    int order() default Integer.MAX_VALUE;

    /**
     * @return 最外部包装类class。
     * <pre>
     *     使用Void.class：文档显示结果不包装
     * </pre>
     */
    Class<? extends Result> wrapperClass() default Result.class;
}
