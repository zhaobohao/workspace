package com.gitee.sop.sopauth.auth;

import lombok.Data;

/**
 * 使用app_auth_code换取app_auth_token返回结果
 * @author tanghc
 */
@Data
public class FetchTokenResult {
    /**
     * 授权令牌
     */
    private String app_auth_token;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 令牌有效期. -1:永久有效
     */
    private long expires_in = -1;

    /**
     * 刷新令牌有效期. -1:永久有效
     */
    private long re_expires_in = -1;

    /**
     * 刷新令牌时使用。
     * 刷新令牌后，我们会保证老的app_auth_token从刷新开始10分钟内可继续使用，请及时替换为最新token
     */
    private String app_refresh_token;

    private String error;
    private String error_description;
}
