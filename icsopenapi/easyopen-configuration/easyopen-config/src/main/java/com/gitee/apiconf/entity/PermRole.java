package com.gitee.apiconf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：perm_role
 * 备注：角色表
 */
@Table(name = "perm_role")
public class PermRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 角色代码, 数据库字段：role_code */
    private String roleCode;

    /** 角色描述, 数据库字段：description */
    private String description;

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

    /** 设置角色代码,数据库字段：perm_role.role_code */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /** 获取角色代码,数据库字段：perm_role.role_code */
    public String getRoleCode() {
        return this.roleCode;
    }

    /** 设置角色描述,数据库字段：perm_role.description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 获取角色描述,数据库字段：perm_role.description */
    public String getDescription() {
        return this.description;
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

        PermRole other = (PermRole) obj;

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
        sb.append("PermRole [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("roleCode=").append(roleCode);
        sb.append(", ");
        sb.append("description=").append(description);
        sb.append(", ");
        sb.append("gmtCreate=").append(gmtCreate);
        sb.append(", ");
        sb.append("gmtUpdate=").append(gmtUpdate);
        sb.append("]");

        return sb.toString();
    }
}
