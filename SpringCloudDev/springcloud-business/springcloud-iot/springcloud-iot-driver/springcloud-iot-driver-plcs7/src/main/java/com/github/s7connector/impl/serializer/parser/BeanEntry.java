package com.github.s7connector.impl.serializer.parser;

import com.github.s7connector.api.S7Serializable;
import com.github.s7connector.api.S7Type;

import java.lang.reflect.Field;

/**
 * A Bean-Entry
 *
 * @author Thomas Rudin
 */
public final class BeanEntry {
    /**
     * The Array size
     */
    public int arraySize;

    /**
     * Offsets and size
     */
    public int byteOffset, bitOffset, size;

    /**
     * The corresponding field
     */
    public Field field;

    /**
     * Array type
     */
    public boolean isArray;

    /**
     * The S7 Type
     */
    public S7Type s7type;

    /**
     * The corresponding serializer
     */
    public S7Serializable serializer;

    /**
     * The Java type
     */
    public Class<?> type;
}
