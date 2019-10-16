package com.gitee.sop.gateway.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：perm_role_permission
 * 备注：角色权限表
 *
 * @author tanghc
 */
@Table(name = "perm_role_permission")
@Data
public class PermRolePermission {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 角色表code, 数据库字段：role_code */
    private String roleCode;

    /** api_id, 数据库字段：route_id */
    private String routeId;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
