package com.gitee.easyopen.register;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

/**
 * @author tanghc
 */
public abstract class AbstractValidationAnnotationBuilder<T extends Annotation> implements ValidationAnnotationBuilder<T> {
    @Override
    public ValidationAnnotationDefinition build(T jsr303Annotation) {
        ValidationAnnotationDefinition validationAnnotationDefinition = new ValidationAnnotationDefinition();

        validationAnnotationDefinition.setAnnotationClass(jsr303Annotation.annotationType());
        Map<String, Object> properties = AnnotationUtils.getAnnotationAttributes(jsr303Annotation);
        properties = this.formatProperties(properties);
        validationAnnotationDefinition.setProperties(properties);
        return validationAnnotationDefinition;
    }

    protected Map<String, Object> formatProperties(Map<String, Object> properties) {
        Set<Map.Entry<String, Object>> entrySet = properties.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            Object value = entry.getValue();
            if (value.getClass().isArray()) {
                Object[] arr = (Object[])value;
                if (arr.length == 0) {
                    properties.put(entry.getKey(), null);
                }
            }
        }
        return properties;
    }
}
