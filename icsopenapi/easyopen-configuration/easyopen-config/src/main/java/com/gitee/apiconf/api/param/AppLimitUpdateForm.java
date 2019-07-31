package com.gitee.apiconf.api.param;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class AppLimitUpdateForm extends AppParam {

    @NotEmpty(message = "apiIds不能为空")
    private List<Long> apiIds;

    /** 限流策略, 数据库字段：limit_type */
    private String limitType;

    /** 每秒可处理请求数, 数据库字段：limit_count */
    @Max(value = 99999999,message = "每秒处理请求数必须小于100000000")
    private Integer limitCount;

    /** 返回的错误码, 数据库字段：limit_code */
    @Length(max = 50)
    private String limitCode;

    /** 返回的错误信息, 数据库字段：limit_msg */
    @Length(max = 100)
    private String limitMsg;

    /** 令牌桶容量, 数据库字段：token_bucket_count */
    @Max(value = 99999999, message = "令牌桶容量必须小于100000000")
    private Integer tokenBucketCount;

    /** 1:开启，0关闭, 数据库字段：status */
    private Byte status;

    public List<Long> getApiIds() {
        return apiIds;
    }

    public void setApiIds(List<Long> apiIds) {
        this.apiIds = apiIds;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
