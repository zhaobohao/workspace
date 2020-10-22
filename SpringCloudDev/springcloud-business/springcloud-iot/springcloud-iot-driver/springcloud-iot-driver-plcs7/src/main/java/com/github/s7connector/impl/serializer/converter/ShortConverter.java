package com.github.s7connector.impl.serializer.converter;

import com.github.s7connector.api.S7Serializable;
import com.github.s7connector.api.S7Type;

public class ShortConverter implements S7Serializable {

    private static final short OFFSET_HIGH_BYTE = 0;
    private static final short OFFSET_LOW_BYTE = 1;

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T extract(final Class<T> targetClass, final byte[] buffer, final int byteOffset, final int bitOffset) {
        final byte lower = buffer[byteOffset + OFFSET_LOW_BYTE];
        final byte higher = buffer[byteOffset + OFFSET_HIGH_BYTE];

        final Integer i = (lower & 0xFF) | ((higher << 8) & 0xFF00);

        return targetClass.cast(i.shortValue());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S7Type getS7Type() {
        return S7Type.INT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSizeInBits() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSizeInBytes() {
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final Object javaType, final byte[] buffer, final int byteOffset, final int bitOffset,
                       final int size) {
        final Short value = (Short) javaType;
        final byte lower = (byte) ((value >> 0) & 0xFF);
        final byte higher = (byte) ((value >> 8) & 0xFF);
        buffer[byteOffset + OFFSET_LOW_BYTE] = lower;
        buffer[byteOffset + OFFSET_HIGH_BYTE] = higher;
    }

}
