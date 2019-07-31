package com.gitee.apiconf.api.result;

public class LimitAppConfigVo {
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

    /** 接口描述, 数据库字段：description */
    private String description;

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
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public String getLimitCode() {
        return limitCode;
    }

    public void setLimitCode(String limitCode) {
        this.limitCode = limitCode;
    }

    public String getLimitMsg() {
        return limitMsg;
    }

    public void setLimitMsg(String limitMsg) {
        this.limitMsg = limitMsg;
    }

    public Integer getTokenBucketCount() {
        return tokenBucketCount;
    }

    public void setTokenBucketCount(Integer tokenBucketCount) {
        this.tokenBucketCount = tokenBucketCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
