package com.gitee.easyopen.register;

import java.util.Map;

/**
 * @author tanghc
 */
public class ValidationAnnotationDefinition {
    private Class<?> annotationClass;
    private Map<String, Object> properties;

    public Class<?> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<?> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
