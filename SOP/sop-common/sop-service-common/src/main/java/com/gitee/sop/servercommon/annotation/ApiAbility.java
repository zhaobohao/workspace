package com.gitee.sop.servercommon.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置接口能力。
 * 如果想把已经存在的接口开放出去，可用此注解。<br>
 * 作用于Controller类上或方法上。如果作用在类上，则类中的所有方法将具备开放平台接口提供能力。
 * @author tanghc
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiAbility {
    /**
     * 版本号，如：1.0
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
}
