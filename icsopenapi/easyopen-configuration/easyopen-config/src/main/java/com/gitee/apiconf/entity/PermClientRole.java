package com.gitee.apiconf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：perm_client_role
 * 备注：客户端角色
 */
@Table(name = "perm_client_role")
public class PermClientRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 客户端id, 数据库字段：client_id */
    private Long clientId;

    /** 角色code, 数据库字段：role_code */
    private String roleCode;

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

    /** 设置客户端id,数据库字段：perm_client_role.client_id */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /** 获取客户端id,数据库字段：perm_client_role.client_id */
    public Long getClientId() {
        return this.clientId;
    }

    /** 设置角色code,数据库字段：perm_client_role.role_code */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /** 获取角色code,数据库字段：perm_client_role.role_code */
    public String getRoleCode() {
        return this.roleCode;
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

        PermClientRole other = (PermClientRole) obj;

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
        sb.append("PermClientRole [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("clientId=").append(clientId);
        sb.append(", ");
        sb.append("roleCode=").append(roleCode);
        sb.append(", ");
        sb.append("gmtCreate=").append(gmtCreate);
        sb.append(", ");
        sb.append("gmtUpdate=").append(gmtUpdate);
        sb.append("]");

        return sb.toString();
    }
}
