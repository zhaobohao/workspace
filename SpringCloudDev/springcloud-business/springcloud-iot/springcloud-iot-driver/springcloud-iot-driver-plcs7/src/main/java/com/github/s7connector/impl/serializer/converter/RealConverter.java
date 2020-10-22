package com.github.s7connector.impl.serializer.converter;

import com.github.s7connector.api.S7Serializable;
import com.github.s7connector.api.S7Type;

public final class RealConverter implements S7Serializable {

    private static final int OFFSET_POS1 = 0;
    private static final int OFFSET_POS2 = 1;
    private static final int OFFSET_POS3 = 2;
    private static final int OFFSET_POS4 = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T extract(final Class<T> targetClass, final byte[] buffer, final int byteOffset, final int bitOffset) {
        final int iValue = ((buffer[byteOffset + OFFSET_POS4] & 0xFF) << 0)
                | ((buffer[byteOffset + OFFSET_POS3] & 0xFF) << 8) | ((buffer[byteOffset + OFFSET_POS2] & 0xFF) << 16)
                | ((buffer[byteOffset + OFFSET_POS1] & 0xFF) << 24);

        final Float fValue = Float.intBitsToFloat(iValue);

        Object ret = fValue;

        if (targetClass == Double.class) {
            ret = Double.parseDouble(fValue.toString());
        }

        return targetClass.cast(ret);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S7Type getS7Type() {
        return S7Type.REAL;
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
        final float fValue = Float.parseFloat(javaType.toString());

        final int iValue = Float.floatToIntBits(fValue);

        buffer[byteOffset + OFFSET_POS4] = (byte) ((iValue >> 0) & 0xFF);
        buffer[byteOffset + OFFSET_POS3] = (byte) ((iValue >> 8) & 0xFF);
        buffer[byteOffset + OFFSET_POS2] = (byte) ((iValue >> 16) & 0xFF);
        buffer[byteOffset + OFFSET_POS1] = (byte) ((iValue >> 24) & 0xFF);
    }

}
