package com.gitee.easyopen.doc.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 文档注解，作用在Service类上
 * @author tanghc
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ApiDoc {

    /**
     * @return 文档模块名
     */
    String value();
    
    /**
     * @return 指定模块显示顺序，值越小越靠前
     */
    int order() default Integer.MAX_VALUE;
}
