package com.gitee.apiconf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：app_info
 * 备注：应用信息表
 */
@Table(name = "app_info")
public class AppInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** app名称, 数据库字段：app */
    private String app;

    /** 文档页面url, 数据库字段：doc_url */
    private String docUrl;

    /** 文档页面密码, 数据库字段：doc_password */
    private String docPassword;

    /** 文档状态，1:开启，0：关闭, 数据库字段：doc_status */
    private Byte docStatus;

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

    /** 设置app名称,数据库字段：app_info.app */
    public void setApp(String app) {
        this.app = app;
    }

    /** 获取app名称,数据库字段：app_info.app */
    public String getApp() {
        return this.app;
    }

    /** 设置文档页面url,数据库字段：app_info.doc_url */
    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    /** 获取文档页面url,数据库字段：app_info.doc_url */
    public String getDocUrl() {
        return this.docUrl;
    }

    /** 设置文档页面密码,数据库字段：app_info.doc_password */
    public void setDocPassword(String docPassword) {
        this.docPassword = docPassword;
    }

    /** 获取文档页面密码,数据库字段：app_info.doc_password */
    public String getDocPassword() {
        return this.docPassword;
    }

    /** 设置文档状态，1:开启，0：关闭,数据库字段：app_info.doc_status */
    public void setDocStatus(Byte docStatus) {
        this.docStatus = docStatus;
    }

    /** 获取文档状态，1:开启，0：关闭,数据库字段：app_info.doc_status */
    public Byte getDocStatus() {
        return this.docStatus;
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

        AppInfo other = (AppInfo) obj;

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
        sb.append("AppInfo [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("app=").append(app);
        sb.append(", ");
        sb.append("docUrl=").append(docUrl);
        sb.append(", ");
        sb.append("docPassword=").append(docPassword);
        sb.append(", ");
        sb.append("docStatus=").append(docStatus);
        sb.append(", ");
        sb.append("gmtCreate=").append(gmtCreate);
        sb.append(", ");
        sb.append("gmtUpdate=").append(gmtUpdate);
        sb.append("]");

        return sb.toString();
    }
}
