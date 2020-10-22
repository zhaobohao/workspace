package com.github.s7connector.impl.serializer.converter;

import com.github.s7connector.api.S7Serializable;
import com.github.s7connector.api.S7Type;

public final class LongConverter implements S7Serializable {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T extract(final Class<T> targetClass, final byte[] buffer, final int byteOffset, final int bitOffset) {
        final byte b1 = buffer[byteOffset + 0];
        final byte b2 = buffer[byteOffset + 1];
        final byte b3 = buffer[byteOffset + 2];
        final byte b4 = buffer[byteOffset + 3];

        final Integer i =
                ((b4 << 0) & 0x000000FF) |
                        ((b3 << 8) & 0x0000FF00) |
                        ((b2 << 16) & 0x00FF0000) |
                        ((b1 << 24) & 0xFF000000);

        return targetClass.cast(i.longValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S7Type getS7Type() {
        return S7Type.DWORD;
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
        return 4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final Object javaType, final byte[] buffer, final int byteOffset, final int bitOffset,
                       final int size) {
        final Long value = (Long) javaType;
        final byte b4 = (byte) ((value >> 0) & 0xFF);
        final byte b3 = (byte) ((value >> 8) & 0xFF);
        final byte b2 = (byte) ((value >> 16) & 0xFF);
        final byte b1 = (byte) ((value >> 24) & 0xFF);
        buffer[byteOffset + 0] = b1;
        buffer[byteOffset + 1] = b2;
        buffer[byteOffset + 2] = b3;
        buffer[byteOffset + 3] = b4;
    }

}
