package com.gitee.easyopen.bean;

import java.io.Serializable;

/**
 * @author tanghc
 */
public class Secret implements Serializable {
    private static final long serialVersionUID = 3037704098651471613L;

    /** appKey */
    private String appKey;

    /** secret */
    private String secret;

    /** 公钥 */
    private String pubKey;

    /** 私钥 */
    private String priKey;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }
    
}
