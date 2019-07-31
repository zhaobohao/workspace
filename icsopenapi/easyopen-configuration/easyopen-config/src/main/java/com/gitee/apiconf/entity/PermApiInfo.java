package com.gitee.apiconf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：perm_api_info
 * 备注：接口信息表
 */
@Table(name = "perm_api_info")
public class PermApiInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 接口名, 数据库字段：name */
    private String name;

    /** 版本号, 数据库字段：version */
    private String version;

    /** 接口描述, 数据库字段：description */
    private String description;

    /** 模块名, 数据库字段：module_name */
    private String moduleName;

    /** 排序字段, 数据库字段：order_index */
    private Integer orderIndex;

    /** 所属app, 数据库字段：app */
    private String app;

    /** 0：使用中，1：未使用, 数据库字段：status */
    private Byte status;

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

    /** 设置接口名,数据库字段：perm_api_info.name */
    public void setName(String name) {
        this.name = name;
    }

    /** 获取接口名,数据库字段：perm_api_info.name */
    public String getName() {
        return this.name;
    }

    /** 设置版本号,数据库字段：perm_api_info.version */
    public void setVersion(String version) {
        this.version = version;
    }

    /** 获取版本号,数据库字段：perm_api_info.version */
    public String getVersion() {
        return this.version;
    }

    /** 设置接口描述,数据库字段：perm_api_info.description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 获取接口描述,数据库字段：perm_api_info.description */
    public String getDescription() {
        return this.description;
    }

    /** 设置模块名,数据库字段：perm_api_info.module_name */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /** 获取模块名,数据库字段：perm_api_info.module_name */
    public String getModuleName() {
        return this.moduleName;
    }

    /** 设置排序字段,数据库字段：perm_api_info.order_index */
    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    /** 获取排序字段,数据库字段：perm_api_info.order_index */
    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    /** 设置所属app,数据库字段：perm_api_info.app */
    public void setApp(String app) {
        this.app = app;
    }

    /** 获取所属app,数据库字段：perm_api_info.app */
    public String getApp() {
        return this.app;
    }

    /** 设置0：使用中，1：未使用,数据库字段：perm_api_info.status */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /** 获取0：使用中，1：未使用,数据库字段：perm_api_info.status */
    public Byte getStatus() {
        return this.status;
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

        PermApiInfo other = (PermApiInfo) obj;

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
        sb.append("PermApiInfo [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("name=").append(name);
        sb.append(", ");
        sb.append("version=").append(version);
        sb.append(", ");
        sb.append("description=").append(description);
        sb.append(", ");
        sb.append("moduleName=").append(moduleName);
        sb.append(", ");
        sb.append("orderIndex=").append(orderIndex);
        sb.append(", ");
        sb.append("app=").append(app);
        sb.append(", ");
        sb.append("status=").append(status);
        sb.append(", ");
        sb.append("gmtCreate=").append(gmtCreate);
        sb.append(", ");
        sb.append("gmtUpdate=").append(gmtUpdate);
        sb.append("]");

        return sb.toString();
    }
}
