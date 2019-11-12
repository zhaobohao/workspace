package com.gitee.sop.servercommon.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口申明注解，使用方式同RequestMapping一样，多了一个版本号属性，
 * 用了此注解具备开放平台接口提供能力。
 * @author tanghc
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface ApiMapping {

    // ------------ 自定义属性 ------------

    /**
     * 版本号，默认版本号是""<br>
     *     改默认版本号：<code>ServiceConfig.getInstance().setDefaultVersion("1.0");</code>
     */
    String version() default "";

    /**
     * 忽略验证，业务参数除外
     */
    boolean ignoreValidate() default false;

    /**
     * 告诉网关是否对结果进行合并，默认合并。设置为false，客户端将直接收到微服务端的结果。
     */
    boolean mergeResult() default true;

    /**
     * 指定接口是否需要授权才能访问，可在admin中进行修改
     */
    boolean permission() default false;

    /**
     * 是否需要appAuthToken，设置为true，网关端会校验token是否存在
     */
    boolean needToken() default false;

    // ------------ 自定义属性 end ------------



    // ============ 以下是springmvc自带的属性 ============

    /**
     * 接口名
     * Alias for {@link RequestMapping#value}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};


    @AliasFor(annotation = RequestMapping.class)
    RequestMethod[] method() default {};

    /**
     * Alias for {@link RequestMapping#params}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] params() default {};

    /**
     * Alias for {@link RequestMapping#headers}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] headers() default {};

    /**
     * Alias for {@link RequestMapping#consumes}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] consumes() default {};

    /**
     * Alias for {@link RequestMapping#produces}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] produces() default {};
}
