package com.gitee.sop.sopauth.auth;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class OAuth2Config {

    private static OAuth2Config instance = new OAuth2Config();

    public static OAuth2Config getInstance() {
        return instance;
    }

    public static void setInstance(OAuth2Config instance) {
        OAuth2Config.instance = instance;
    }

    /**
     * 应用授权的app_auth_code唯一的；app_auth_code使用一次后失效，一天（从生成app_auth_code开始的24小时）未被使用自动过期；
     * app_auth_token永久有效。
     */
    private int codeTimeoutSeconds = 86400;
    /**
     * accessToken有效时间，单位秒
     * -1 永久有效
     */
    private int accessTokenExpiresIn = -1;

    /**
     * app_refresh_token有效时间，单位秒，accessToken有效时间的3倍。当小于0，永久有效
     */
    private int refreshTokenExpiresIn = accessTokenExpiresIn * 3;

    /**
     * 刷新令牌后，我们会保证老的app_auth_token从刷新开始10分钟内可继续使用，请及时替换为最新token
     */
    private int afterRefreshExpiresIn = 60 * 10;

    /**
     * 登录视图页面用于，mvc视图，如：loginView
     */
    private String oauth2LoginUri = "/oauth2/login";
}
