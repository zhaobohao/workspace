package com.gitee.easyopen.doc.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 文档返回，仅返回基本类型获取字符串时有效
 * @author tanghc
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ApiDocReturn {
    /**
     * @return 返回描述
     */
    String description() default "";

    /**
     * @return 示例值
     */
    String example() default "";

}
