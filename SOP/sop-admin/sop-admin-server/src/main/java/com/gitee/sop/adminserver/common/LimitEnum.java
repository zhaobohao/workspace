package com.gitee.sop.adminserver.common;

/**
 * 限流
 * @author tanghc
 */
public enum LimitEnum {
    /**
     * 限流策略，1：漏桶策略
     */
    TYPE_LEAKY_BUCKET((byte) 1),
    /**
     * 限流策略，2：令牌桶策略
     */
    TYPE_TOKEN_BUCKET((byte) 2),
    /**
     * 1:开启
     */
    STATUS_OPEN((byte) 1),
    /**
     * 0关闭
     */
    STATUS_CLOSE((byte) 0),

    ;

    LimitEnum(byte val) {
        this.val = val;
    }

    private byte val;

    public byte getVal() {
        return val;
    }
}
