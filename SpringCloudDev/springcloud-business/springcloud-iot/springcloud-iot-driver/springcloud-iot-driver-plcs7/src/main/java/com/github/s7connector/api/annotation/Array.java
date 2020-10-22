package com.github.s7connector.api.annotation;

import java.lang.annotation.*;

/**
 * Annotation for array-declaration
 *
 * @author Thomas Rudin
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Array {
    int size();
}
