package com.gitee.sop.adminserver.bean;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class RouteConfigDto {

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 状态，0：待审核，1：启用，2：禁用
     */
    private Integer status;


}
