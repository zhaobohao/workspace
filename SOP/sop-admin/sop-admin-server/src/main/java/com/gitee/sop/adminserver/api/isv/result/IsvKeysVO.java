package com.gitee.sop.adminserver.api.isv.result;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class IsvKeysVO {

    /** appKey, 数据库字段：app_key */
    @ApiDocField(description = "appKey", example = "aaaa")
    private String appKey;

    /** secret, 数据库字段：secret */
    @ApiDocField(description = "secret", example = "bbbb")
    private String secret;

    /** 秘钥格式，1：PKCS8(JAVA适用)，2：PKCS1(非JAVA适用), 数据库字段：key_format */
    private Byte keyFormat = 1;

    /** 开发者生成的公钥, 数据库字段：public_key_isv */
    private String publicKeyIsv;

    /** 开发者生成的私钥（交给开发者）, 数据库字段：private_key_isv */
    private String privateKeyIsv;

    /** 平台生成的公钥（交给开发者）, 数据库字段：public_key_platform */
    private String publicKeyPlatform;

    /** 平台生成的私钥, 数据库字段：private_key_platform */
    private String privateKeyPlatform;

    @ApiDocField(description = "签名类型：1:RSA2,2:MD5")
    private Byte signType = 1;

}
