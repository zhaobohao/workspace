package com.gitee.sop.gatewaycommon.bean;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class RouteConfig {

    private static final byte STATUS_ENABLE = RouteStatus.ENABLE.getStatus();

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 状态，0：待审核，1：启用，2：禁用。默认启用
     */
    private Byte status;

    /**
     * 是否启用
     *
     * @return true：启用
     */
    public boolean enable() {
        return status == STATUS_ENABLE;
    }
}
