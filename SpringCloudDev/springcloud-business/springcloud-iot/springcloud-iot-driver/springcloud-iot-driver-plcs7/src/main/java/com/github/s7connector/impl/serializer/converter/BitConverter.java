package com.github.s7connector.impl.serializer.converter;

import com.github.s7connector.api.S7Serializable;
import com.github.s7connector.api.S7Type;

/**
 * The Class BitConverter is responsible for converting bit values
 */
public final class BitConverter implements S7Serializable {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T extract(final Class<T> targetClass, final byte[] buffer, final int byteOffset, final int bitOffset) {
        final byte bufValue = buffer[byteOffset];
        return targetClass.cast(bufValue == (bufValue | (0x01 << bitOffset)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S7Type getS7Type() {
        return S7Type.BOOL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSizeInBits() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSizeInBytes() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final Object javaType, final byte[] buffer, final int byteOffset, final int bitOffset,
                       final int size) {
        final Boolean value = (Boolean) javaType;

        //thx to @mfriedemann (https://github.com/mfriedemann)
        if (value) {
            buffer[byteOffset] |= (0x01 << bitOffset);
        } else {
            buffer[byteOffset] &= ~(0x01 << bitOffset);
        }
    }

}
