package com.gitee.sop.sopauth.auth;

/**
 * 存放accessToken和refreshToken
 * 
 * @author tanghc
 *
 */
public class TokenPair {
    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
