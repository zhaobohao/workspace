package com.github.s7connector.impl.serializer.converter;

import com.github.s7connector.api.S7Serializable;
import com.github.s7connector.api.S7Type;

public final class StringConverter implements S7Serializable {

    private static final int OFFSET_CURRENT_LENGTH = 1;
    private static final int OFFSET_OVERALL_LENGTH = 0;
    private static final int OFFSET_START = 2;

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T extract(final Class<T> targetClass, final byte[] buffer, final int byteOffset, final int bitOffset) {
        final int len = buffer[byteOffset + OFFSET_CURRENT_LENGTH] & 0xFF;

        return targetClass.cast(new String(buffer, byteOffset + OFFSET_START, len));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S7Type getS7Type() {
        return S7Type.STRING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSizeInBits() {
        // Not static
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSizeInBytes() {
        // Not static
        return 2; // 2 bytes overhead
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final Object javaType, final byte[] buffer, final int byteOffset, final int bitOffset,
                       final int size) {
        final String value = (String) javaType;

        final int len = value.length();

        if (len > size) {
            throw new IllegalArgumentException("String to big: " + len);
        }

        buffer[byteOffset + OFFSET_OVERALL_LENGTH] = (byte) size;
        buffer[byteOffset + OFFSET_CURRENT_LENGTH] = (byte) len;

        final byte[] strBytes = value.getBytes();
        System.arraycopy(strBytes, 0, buffer, byteOffset + OFFSET_START, len);
    }

}
