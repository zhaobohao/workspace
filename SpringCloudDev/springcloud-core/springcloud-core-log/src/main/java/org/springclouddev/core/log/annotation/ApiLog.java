

package org.springclouddev.core.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author firewan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

	/**
	 * 日志描述
	 *
	 * @return {String}
	 */
	String value() default "日志记录";
}
