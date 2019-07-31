package com.gitee.easyopen.register;

import com.gitee.easyopen.util.ReflectionUtil;

import java.lang.annotation.Annotation;

/**
 * @author tanghc
 */
public abstract class BaseValidationAnnotationBuilder<T extends Annotation> extends AbstractValidationAnnotationBuilder<T> {
    public BaseValidationAnnotationBuilder() {
        Class<?> clazz = ReflectionUtil.getSuperClassGenricType(getClass(), 0);
        ValidationAnnotationDefinitionFactory.addBuilder(clazz, this);
    }
}