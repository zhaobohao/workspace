package com.gitee.easyopen.limit;

/**
 * 限流策略
 * 
 * @author tanghc
 */
public enum LimitType {
    /**
     * 漏桶策略。每秒处理固定数量的请求，超出请求返回错误信息。
     */
    LIMIT("LIMIT"),
    /**
     * 令牌桶策略，每秒放置固定数量的令牌数，不足的令牌数做等待处理，直到拿到令牌为止。
     */
    TOKEN_BUCKET("TOKEN_BUCKET");

    private String type;

    LimitType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
