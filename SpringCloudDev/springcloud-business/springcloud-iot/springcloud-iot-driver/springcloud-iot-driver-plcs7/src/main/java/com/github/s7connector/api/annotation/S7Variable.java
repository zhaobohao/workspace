package com.github.s7connector.api.annotation;

import com.github.s7connector.api.S7Type;

import java.lang.annotation.*;

/**
 * Defines an Offset in a DB
 *
 * @author Thomas Rudin
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface S7Variable {
    /**
     * The size of the array
     */
    int arraySize() default 1;

    /**
     * The bit offset, if any
     */
    int bitOffset() default 0;

    /**
     * The Byte Offset
     */
    int byteOffset();

    /**
     * The specified size (for String)
     */
    int size() default 0;

    /**
     * The corresponding S7 Type
     */
    S7Type type();

}
