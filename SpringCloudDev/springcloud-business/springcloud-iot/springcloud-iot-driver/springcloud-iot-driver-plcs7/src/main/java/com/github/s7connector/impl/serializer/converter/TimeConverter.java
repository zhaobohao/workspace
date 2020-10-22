package com.github.s7connector.impl.serializer.converter;

import com.github.s7connector.api.S7Type;

public final class TimeConverter extends ByteConverter {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T extract(final Class<T> targetClass, final byte[] buffer, final int byteOffset, final int bitOffset) {
        final byte b1 = super.extract(Byte.class, buffer, byteOffset + 3, bitOffset);
        final byte b2 = super.extract(Byte.class, buffer, byteOffset + 2, bitOffset);
        final byte b3 = super.extract(Byte.class, buffer, byteOffset + 1, bitOffset);
        final byte b4 = super.extract(Byte.class, buffer, byteOffset + 0, bitOffset);

        final long l = ((long) b1 & 0xFF) << 0 | ((long) b2 & 0xFF) << 8 | ((long) b3 & 0xFF) << 16
                | ((long) b4 & 0xFF) << 24;

        return targetClass.cast(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S7Type getS7Type() {
        return S7Type.TIME;
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
        final long l = (Long) javaType;

        final byte b1 = (byte) ((byte) (l >> 0) & 0xFF);
        final byte b2 = (byte) ((byte) (l >> 8) & 0xFF);
        final byte b3 = (byte) ((byte) (l >> 16) & 0xFF);
        final byte b4 = (byte) ((byte) (l >> 24) & 0xFF);

        super.insert(b1, buffer, byteOffset + 3, bitOffset, 1);
        super.insert(b2, buffer, byteOffset + 2, bitOffset, 1);
        super.insert(b3, buffer, byteOffset + 1, bitOffset, 1);
        super.insert(b4, buffer, byteOffset + 0, bitOffset, 1);
    }

}
