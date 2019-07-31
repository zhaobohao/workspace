package com.gitee.easyopen.limit;

/**
 * @author tanghc
 */
public enum LimitStatus {
    /**
     * 开启限流
     */
    OPEN(1),
    /**
     * 关闭限流
     */
    CLOSE(0);

    private byte status;

    LimitStatus(Integer status) {
        this.status = status.byteValue();
    }

    public byte getStatus() {
        return status;
    }

}
