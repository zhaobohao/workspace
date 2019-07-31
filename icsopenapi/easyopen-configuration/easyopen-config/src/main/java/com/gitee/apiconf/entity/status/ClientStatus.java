package com.gitee.apiconf.entity.status;

/**
 * @author tanghc
 */
public enum ClientStatus {
    ENABLE((byte) 0, "启用"),
    DISABLE((byte) 1, "禁用"),;
    //0启用，1禁用
    private byte status;
    private String remark;

    ClientStatus(byte status, String remark) {
        this.status = status;
        this.remark = remark;
    }

    public byte getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }
}
