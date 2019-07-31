package com.gitee.apiconf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：limit_app_config
 * 备注：限流配置
 */
@Table(name = "limit_app_config")
public class LimitAppConfig {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** perm_api_info.id, 数据库字段：api_id */
    private Long apiId;

    /** app, 数据库字段：app */
    private String app;

    /** 接口名, 数据库字段：name */
    private String name;

    /** 版本号, 数据库字段：version */
    private String version;

    /** 限流策略, 数据库字段：limit_type */
    private String limitType;

    /** 每秒可处理请求数, 数据库字段：limit_count */
    private Integer limitCount;

    /** 返回的错误码, 数据库字段：limit_code */
    private String limitCode;

    /** 返回的错误信息, 数据库字段：limit_msg */
    private String limitMsg;

    /** 令牌桶容量, 数据库字段：token_bucket_count */
    private Integer tokenBucketCount;

    /** 1:开启，0关闭, 数据库字段：status */
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

    /** 设置perm_api_info.id,数据库字段：limit_app_config.api_id */
    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    /** 获取perm_api_info.id,数据库字段：limit_app_config.api_id */
    public Long getApiId() {
        return this.apiId;
    }

    /** 设置app,数据库字段：limit_app_config.app */
    public void setApp(String app) {
        this.app = app;
    }

    /** 获取app,数据库字段：limit_app_config.app */
    public String getApp() {
        return this.app;
    }

    /** 设置接口名,数据库字段：limit_app_config.name */
    public void setName(String name) {
        this.name = name;
    }

    /** 获取接口名,数据库字段：limit_app_config.name */
    public String getName() {
        return this.name;
    }

    /** 设置版本号,数据库字段：limit_app_config.version */
    public void setVersion(String version) {
        this.version = version;
    }

    /** 获取版本号,数据库字段：limit_app_config.version */
    public String getVersion() {
        return this.version;
    }

    /** 设置限流策略,数据库字段：limit_app_config.limit_type */
    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    /** 获取限流策略,数据库字段：limit_app_config.limit_type */
    public String getLimitType() {
        return this.limitType;
    }

    /** 设置每秒可处理请求数,数据库字段：limit_app_config.limit_count */
    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    /** 获取每秒可处理请求数,数据库字段：limit_app_config.limit_count */
    public Integer getLimitCount() {
        return this.limitCount;
    }

    /** 设置返回的错误码,数据库字段：limit_app_config.limit_code */
    public void setLimitCode(String limitCode) {
        this.limitCode = limitCode;
    }

    /** 获取返回的错误码,数据库字段：limit_app_config.limit_code */
    public String getLimitCode() {
        return this.limitCode;
    }

    /** 设置返回的错误信息,数据库字段：limit_app_config.limit_msg */
    public void setLimitMsg(String limitMsg) {
        this.limitMsg = limitMsg;
    }

    /** 获取返回的错误信息,数据库字段：limit_app_config.limit_msg */
    public String getLimitMsg() {
        return this.limitMsg;
    }

    /** 设置令牌桶容量,数据库字段：limit_app_config.token_bucket_count */
    public void setTokenBucketCount(Integer tokenBucketCount) {
        this.tokenBucketCount = tokenBucketCount;
    }

    /** 获取令牌桶容量,数据库字段：limit_app_config.token_bucket_count */
    public Integer getTokenBucketCount() {
        return this.tokenBucketCount;
    }

    /** 设置1:开启，0关闭,数据库字段：limit_app_config.status */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /** 获取1:开启，0关闭,数据库字段：limit_app_config.status */
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

        LimitAppConfig other = (LimitAppConfig) obj;

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
        sb.append("LimitAppConfig [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("apiId=").append(apiId);
        sb.append(", ");
        sb.append("app=").append(app);
        sb.append(", ");
        sb.append("name=").append(name);
        sb.append(", ");
        sb.append("version=").append(version);
        sb.append(", ");
        sb.append("limitType=").append(limitType);
        sb.append(", ");
        sb.append("limitCount=").append(limitCount);
        sb.append(", ");
        sb.append("limitCode=").append(limitCode);
        sb.append(", ");
        sb.append("limitMsg=").append(limitMsg);
        sb.append(", ");
        sb.append("tokenBucketCount=").append(tokenBucketCount);
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
