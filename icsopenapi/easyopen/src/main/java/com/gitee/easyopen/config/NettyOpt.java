package com.gitee.easyopen.config;

/**
 * netty操作码
 * @author tanghc
 */
public enum NettyOpt {
    /**
     * 同步本地API到配置中心
     */
    SYNC_APP_API,
    /**
     * 客户端连接成功
     */
    CLIENT_CONNECTED,
    /**
     * 下载限流配置
     */
    DOWNLOAD_LIMIT_CONFIG,
    /**
     * 更新限流配置
     */
    UPDATE_LIMIT_CONFIG,
    /**
     * 下载权限配置
     */
    DOWNLOAD_PERMISSION_CONFIG,
    /**
     * 更新权限配置
     */
    UPDATE_PERMISSION_CONFIG,
    /**
     * 下载秘钥配置
     */
    DOWNLOAD_SECRET_CONFIG,
    /**
     * 更新秘钥配置
     */
    UPDATE_SECRET_CONFIG,
    /**
     * 心跳检测
     */
    HEART_BEAT,
    ;

    public String getCode() {
        return this.name();
    }

}
