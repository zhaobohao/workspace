package com.gitee.sop.sopauth.auth;

import java.io.Serializable;

/**
 * @author tanghc
 */
public class RefreshToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accessToken;
    private OpenUser openUser;

    public RefreshToken(String accessToken, OpenUser openUser) {
        super();
        this.accessToken = accessToken;
        this.openUser = openUser;
    }

    public RefreshToken() {
        super();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public OpenUser getOpenUser() {
        return openUser;
    }

    public void setOpenUser(OpenUser openUser) {
        this.openUser = openUser;
    }

}
