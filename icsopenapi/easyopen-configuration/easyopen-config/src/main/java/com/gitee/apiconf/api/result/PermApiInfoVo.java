package com.gitee.apiconf.api.result;

import java.util.Date;

public class PermApiInfoVo {
    /**  数据库字段：id */
    private Long id;

    /** 接口名, 数据库字段：name */
    private String name;

    /** 版本号, 数据库字段：version */
    private String version;

    /** 接口描述, 数据库字段：description */
    private String description;

    /** 所属app, 数据库字段：app */
    private String app;

    /** 0：使用中，1：未使用, 数据库字段：status */
    private Byte status;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_update */
    private Date gmtUpdate;

    /** 是否已授权 */
    private boolean hasAuthed;

    public boolean isHasAuthed() {
        return hasAuthed;
    }

    public void setHasAuthed(boolean hasAuthed) {
        this.hasAuthed = hasAuthed;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermApiInfoVo that = (PermApiInfoVo) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
