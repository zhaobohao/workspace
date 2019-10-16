package com.gitee.sop.gatewaycommon.bean;

/**
 * 路由状态
 *
 * @author tanghc
 */
public enum RouteStatus {
    /**
     * 路由状态，0：待审核
     */
    AUDIT(0, "待审核"),
    /**
     * 路由状态，1：已启用
     */
    ENABLE(1, "已启用"),
    /**
     * 路由状态，2：已禁用
     */
    DISABLE(2, "已禁用"),
    ;
    private byte status;
    private String description;

    RouteStatus(Integer status, String description) {
        this.status = status.byteValue();
        this.description = description;
    }

    public byte getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }}
