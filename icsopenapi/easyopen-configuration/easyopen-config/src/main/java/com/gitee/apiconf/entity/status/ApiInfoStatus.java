package com.gitee.apiconf.entity.status;

public enum ApiInfoStatus {
    USING(0, "使用中"), UN_USED(1, "未使用");
    private byte status;
    private String remark;

    ApiInfoStatus(Integer status, String remark) {
        this.status = status.byteValue();
        this.remark = remark;
    }

    public byte getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

}
