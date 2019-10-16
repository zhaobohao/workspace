package com.gitee.sop.adminserver.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author tanghc
 */
@Data
public class ServiceRouteInfo {
    /** 服务名称，对应spring.application.name */
    private String serviceId;

    private Date createTime = new Date();

    private Date updateTime = new Date();

    private String description;

    private List<RouteDefinition> routeDefinitionList;

    /** 是否是自定义服务，1：是，0：否 */
    private int custom;
}