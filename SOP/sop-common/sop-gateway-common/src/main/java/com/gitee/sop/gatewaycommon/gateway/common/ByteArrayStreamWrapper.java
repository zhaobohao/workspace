package com.gitee.sop.gatewaycommon.gateway.common;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;

/**
 * byte数组流包装
 *
 * @author tanghc
 */
public class ByteArrayStreamWrapper extends ServletInputStream {

    private byte[] data;
    private int idx = 0;

    /**
     * Creates a new <code>ByteArrayStreamWrapper</code> instance.
     *
     * @param data a <code>byte[]</code> value
     */
    public ByteArrayStreamWrapper(byte[] data) {
        if (data == null) {
            data = new byte[0];
        }
        this.data = data;
    }

    @Override
    public int read() throws IOException {
        if (idx == data.length) {
            return -1;
        }
        // I have to AND the byte with 0xff in order to ensure that it is returned as an unsigned integer
        // the lack of this was causing a weird bug when manually unzipping gzipped request bodies
        return data[idx++] & 0xff;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }
}