package com.github.s7connector.api.annotation;

import java.lang.annotation.*;

/**
 * Annotation for a datablock
 *
 * @author Thomas Rudin
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Datablock {
}
