package com.github.s7connector.api;

import java.io.Closeable;

/**
 * @author Thomas Rudin
 */
public interface S7Connector extends Closeable {
    /**
     * Reads an area
     *
     * @param area
     * @param areaNumber
     * @param bytes
     * @param offset
     * @return
     */
    public byte[] read(DaveArea area, int areaNumber, int bytes, int offset);

    /**
     * Writes an area
     *
     * @param area
     * @param areaNumber
     * @param offset
     * @param buffer
     */
    public void write(DaveArea area, int areaNumber, int offset, byte[] buffer);

}
