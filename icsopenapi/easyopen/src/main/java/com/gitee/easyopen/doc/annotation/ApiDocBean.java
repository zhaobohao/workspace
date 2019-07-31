package com.gitee.easyopen.doc.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author tanghc
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ApiDocBean {

    /**
     * @return 描述
     */
    String description() default "";

    /**
     * @return 字段信息
     */
    ApiDocField[] fields() default {};
}
