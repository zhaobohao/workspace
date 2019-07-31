package com.gitee.easyopen.register;

import java.lang.annotation.Annotation;

/**
 * @author tanghc
 */
public interface ValidationAnnotationBuilder<T extends Annotation> {
    /**
     * 构建验证注解
     *
     * @param jsr303Annotation JSR-303注解
     * @return 返回注解定义
     */
    ValidationAnnotationDefinition build(T jsr303Annotation);
}
