package com.gitee.easyopen.limit;

import java.io.Serializable;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.gitee.easyopen.bean.Pagable;
import com.google.common.util.concurrent.RateLimiter;

/**
 * 接口限流配置
 * 
 * @author tanghc
 */
public class LimitConfig implements Pagable, Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String version;

    /** 限流策略 */
    private String limitType;

    /** 每秒可处理请求数 */
    @Max(value = 99999999,message = "每秒处理请求数必须小于100000000")
    private Integer limitCount;
    @Length(max = 100)
    private String limitCode;
    @Length(max = 100)
    private String limitMsg;

    /** 令牌桶容量 */
    @Max(value = 99999999, message = "令牌桶容量必须小于100000000")
    private Integer tokenBucketCount;

    /** 1:开启，0:禁用 */
    private Byte status;

    @JSONField(serialize = false)
    private volatile RateLimiter rateLimiter;

    public String getNameVersion() {
        return this.name + this.version;
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
        this.rateLimiter = null;
    }

    public RateLimiter fatchRateLimiter() {
        if (rateLimiter == null) {
            synchronized (LimitConfig.class) {
                if (rateLimiter == null) {
                    rateLimiter = RateLimiter.create(this.tokenBucketCount);
                }
            }
        }
        return rateLimiter;
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

}
