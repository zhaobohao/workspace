package com.gitee.apiconf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：perm_role_permission
 * 备注：角色权限表
 */
@Table(name = "perm_role_permission")
public class PermRolePermission {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 角色表code, 数据库字段：role_code */
    private String roleCode;

    /** api_id, 数据库字段：api_id */
    private Long apiId;

    /** app名称, 数据库字段：app */
    private String app;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_update */
    private Date gmtUpdate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    /** 设置角色表code,数据库字段：perm_role_permission.role_code */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /** 获取角色表code,数据库字段：perm_role_permission.role_code */
    public String getRoleCode() {
        return this.roleCode;
    }

    /** 设置api_id,数据库字段：perm_role_permission.api_id */
    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    /** 获取api_id,数据库字段：perm_role_permission.api_id */
    public Long getApiId() {
        return this.apiId;
    }

    /** 设置app名称,数据库字段：perm_role_permission.app */
    public void setApp(String app) {
        this.app = app;
    }

    /** 获取app名称,数据库字段：perm_role_permission.app */
    public String getApp() {
        return this.app;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Date getGmtUpdate() {
        return this.gmtUpdate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        PermRolePermission other = (PermRolePermission) obj;

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PermRolePermission [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("roleCode=").append(roleCode);
        sb.append(", ");
        sb.append("apiId=").append(apiId);
        sb.append(", ");
        sb.append("app=").append(app);
        sb.append(", ");
        sb.append("gmtCreate=").append(gmtCreate);
        sb.append(", ");
        sb.append("gmtUpdate=").append(gmtUpdate);
        sb.append("]");

        return sb.toString();
    }
}
