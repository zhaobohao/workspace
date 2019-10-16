package com.gitee.sop.gatewaycommon.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tanghc
 */
@Getter
@Setter
public class IsvDefinition implements Isv {

    public static final int SIGN_TYPE_RSA2 = 1;

    public IsvDefinition() {
    }

    public IsvDefinition(String appKey, String secret) {
        this.appKey = appKey;
        this.secret = secret;
    }

    private String appKey;

    /** 秘钥,签名方式为MD5时有用 */
    private String secret;

    /** 开发者生成的公钥, 数据库字段：public_key_isv */
    private String publicKeyIsv;

    /** 平台生成的私钥, 数据库字段：private_key_platform */
    private String privateKeyPlatform;

    /** 0启用，1禁用 */
    private Byte status;

    /** 签名类型：1:RSA2,2:MD5 */
    private Byte signType = 1;

    @Override
    public String getSecretInfo() {
        return signType == SIGN_TYPE_RSA2 ? publicKeyIsv : secret;
    }


    @Override
    public String toString() {
        return "IsvDefinition{" +
                "appKey='" + appKey + '\'' +
                ", secret='" + secret + '\'' +
                ", publicKeyIsv='" + publicKeyIsv + '\'' +
                ", privateKeyPlatform='" + privateKeyPlatform + '\'' +
                ", status=" + status +
                ", signType=" + signType +
                '}';
    }
}
