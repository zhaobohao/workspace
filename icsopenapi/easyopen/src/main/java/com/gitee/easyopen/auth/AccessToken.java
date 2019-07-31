package com.gitee.easyopen.auth;

/**
 * @author tanghc
 */
public class AccessToken {
    /** 过期时间,毫秒 */
    private long expireTimeMillis;
    private OpenUser openUser;

    /**
     * @param expireIn 有效时长，秒
     * @param openUser
     */
    public AccessToken(long expireIn, OpenUser openUser) {
        super();
        if(expireIn <= 0) {
            throw new IllegalArgumentException("expireIn必须大于0");
        }
        this.expireTimeMillis = System.currentTimeMillis() + (expireIn * 1000);
        this.openUser = openUser;
    }

    public boolean isExpired() {
        return expireTimeMillis < System.currentTimeMillis();
    }

    public long getExpireTimeMillis() {
        return expireTimeMillis;
    }

    public void setExpireTimeMillis(long expireTimeMillis) {
        this.expireTimeMillis = expireTimeMillis;
    }

    public OpenUser getOpenUser() {
        return openUser;
    }

    public void setOpenUser(OpenUser openUser) {
        this.openUser = openUser;
    }

}
