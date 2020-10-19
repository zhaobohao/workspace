package org.springclouddev.iot.common.base;

/**
 * Converter
 *

 */
public interface Converter<D,T> {
    /**
     * DTO 转 DO
     *
     * @param d Do对象
     */
    void convertToDo(D d);

    /**
     * DO 转 DTO
     *
     * @param d Do对象
     */
    T convert(D d);
}
