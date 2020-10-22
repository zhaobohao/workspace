package com.github.s7connector.api;

/**
 * The Interface S7Serializable API
 *
 * @author Thomas Rudin
 */
public interface S7Serializable {

    /**
     * Extracts a java type from a byte buffer.
     *
     * @param <T>         the generic type
     * @param targetClass the target class
     * @param buffer      the buffer
     * @param byteOffset  the byte offset
     * @param bitOffset   the bit offset
     * @return the t
     */
    public <T> T extract(Class<T> targetClass, byte[] buffer, int byteOffset, int bitOffset);

    /**
     * Returns the S7-Type.
     *
     * @return the s7 type
     */
    public S7Type getS7Type();

    /**
     * Returns the size of the s7 type bytes.
     *
     * @return the size in bits
     */
    public int getSizeInBits();

    /**
     * Returns the size of the s7 type bytes.
     *
     * @return the size in bytes
     */
    public int getSizeInBytes();

    /**
     * Inserts a Java Object to the byte buffer.
     *
     * @param javaType   the java type
     * @param buffer     the buffer
     * @param byteOffset the byte offset
     * @param bitOffset  the bit offset
     * @param size       the size
     */
    public void insert(Object javaType, byte[] buffer, int byteOffset, int bitOffset, int size);
}
