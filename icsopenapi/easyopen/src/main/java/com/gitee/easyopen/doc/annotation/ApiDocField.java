package com.gitee.easyopen.doc.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.gitee.easyopen.doc.DataType;

/**
 * 文档字段
 * @author tanghc
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
public @interface ApiDocField {
    /**
     * @return 字段描述
     */
    String description() default "";

    /**
     * @return 字段名
     */
    String name() default "";

    /**
     * @return 数据类型
     */
    DataType dataType() default DataType.UNKNOW;

    /**
     * @return 是否必填
     */
    boolean required() default false;

    /**
     * @return 示例值
     */
    String example() default "";

    /**
     * @return 指定文档类
     */
    Class<?> beanClass() default Void.class;

    /**
     * @return 数组元素class类型
     */
    Class<?> elementClass() default Void.class;

    /**
     * @return 枚举类class，指定的枚举类必须实现{@link com.gitee.easyopen.doc.IEnum} 接口
     * 文档页会自动显示枚举信息（getCode() + ":" + getDescription()）
     * @since 1.12.5
     */
    Class<?> enumClass() default Void.class;
}
