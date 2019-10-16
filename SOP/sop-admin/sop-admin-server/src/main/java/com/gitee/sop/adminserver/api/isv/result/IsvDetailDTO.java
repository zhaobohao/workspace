package com.gitee.sop.adminserver.api.isv.result;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class IsvDetailDTO {

    /** appKey, 数据库字段：app_key */
    @ApiDocField(description = "appKey", example = "aaaa")
    private String appKey;

    /** 0启用，1禁用, 数据库字段：status */
    @ApiDocField(description = "状态：0启用，1禁用")
    private Byte status;

    /** secret, 数据库字段：secret */
    @ApiDocField(description = "secret", example = "bbbb")
    private String secret;

    /** 开发者生成的公钥, 数据库字段：public_key_isv */
    @ApiDocField(description = "开发者生成的公钥")
    private String publicKeyIsv;

    /** 平台生成的私钥, 数据库字段：private_key_platform */
    @ApiDocField(description = "平台生成的私钥")
    private String privateKeyPlatform;

    @ApiDocField(description = "签名类型：1:RSA2,2:MD5")
    private Byte signType;
}
