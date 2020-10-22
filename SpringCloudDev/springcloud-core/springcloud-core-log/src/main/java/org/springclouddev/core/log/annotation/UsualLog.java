package org.springclouddev.core.log.annotation;

import java.lang.annotation.*;

/**
 * 普通日志注解
 *
 * @author zhaobohao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UsualLog {

	/**
	 * 日志描述
	 *
	 * @return {String}
	 */
	String value() default "日志记录";

	/**
	 * 日志等级
	 * @return
	 */
    String level() default "info";
}
