package com.gitee.sop.adminserver.api.isv.result;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class IsvKeysGenVO {
    /** secret, 数据库字段：secret */
    private String secret;

    /** 开发者生成的公钥, 数据库字段：public_key_isv */
    private String publicKeyIsv;

    /** 开发者生成的私钥（交给开发者）, 数据库字段：private_key_isv */
    private String privateKeyIsv;

    /** 平台生成的公钥（交给开发者）, 数据库字段：public_key_platform */
    private String publicKeyPlatform;

    /** 平台生成的私钥, 数据库字段：private_key_platform */
    private String privateKeyPlatform;

}
