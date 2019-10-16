package com.gitee.sop.adminserver.entity;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class RouteRoleDTO {
    /** routeId, 数据库字段：route_id */
    private String routeId;

    /** 角色代码, 数据库字段：role_code */
    private String roleCode;

    /** 角色描述, 数据库字段：description */
    private String description;
}
