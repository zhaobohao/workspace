package com.gitee.apiconf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：perm_client
 * 备注：app信息表
 */
@Table(name = "perm_client")
public class PermClient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** appKey, 数据库字段：app_key */
    private String appKey;

    /** secret, 数据库字段：secret */
    private String secret;

    /** 公钥, 数据库字段：pub_key */
    private String pubKey;

    /** 私钥, 数据库字段：pri_key */
    private String priKey;

    /** app名称, 数据库字段：app */
    private String app;

    /** 0启用，1禁用, 数据库字段：status */
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

    /** 设置appKey,数据库字段：perm_client.app_key */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /** 获取appKey,数据库字段：perm_client.app_key */
    public String getAppKey() {
        return this.appKey;
    }

    /** 设置secret,数据库字段：perm_client.secret */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /** 获取secret,数据库字段：perm_client.secret */
    public String getSecret() {
        return this.secret;
    }

    /** 设置公钥,数据库字段：perm_client.pub_key */
    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    /** 获取公钥,数据库字段：perm_client.pub_key */
    public String getPubKey() {
        return this.pubKey;
    }

    /** 设置私钥,数据库字段：perm_client.pri_key */
    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    /** 获取私钥,数据库字段：perm_client.pri_key */
    public String getPriKey() {
        return this.priKey;
    }

    /** 设置app名称,数据库字段：perm_client.app */
    public void setApp(String app) {
        this.app = app;
    }

    /** 获取app名称,数据库字段：perm_client.app */
    public String getApp() {
        return this.app;
    }

    /** 设置0启用，1禁用,数据库字段：perm_client.status */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /** 获取0启用，1禁用,数据库字段：perm_client.status */
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

        PermClient other = (PermClient) obj;

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
        sb.append("PermClient [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("appKey=").append(appKey);
        sb.append(", ");
        sb.append("secret=").append(secret);
        sb.append(", ");
        sb.append("pubKey=").append(pubKey);
        sb.append(", ");
        sb.append("priKey=").append(priKey);
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
