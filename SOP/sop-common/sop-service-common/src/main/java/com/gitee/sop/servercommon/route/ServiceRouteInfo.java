package com.gitee.sop.servercommon.route;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author tanghc
 */
@Data
public class ServiceRouteInfo {

    /**
     * 服务名称，对应spring.application.name
     */
    private String serviceId;

    private Date createTime = new Date();

    private Date updateTime = new Date();

    private String description;

    private List<RouteDefinition> routeDefinitionList;

}