package com.gitee.sop.adminserver.api.isv.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Data
public class IsvKeysFormUpdate {
    /** appKey, 数据库字段：app_key */
    @ApiDocField(description = "appKey", example = "aaaa")
    @NotBlank(message = "appKey不能为空")
    @Length(max = 100,message = "appKey长度不能超过100")
    private String appKey;

    /** secret, 数据库字段：secret */
    @ApiDocField(description = "secret", example = "bbbb")
    private String secret;

    /** 秘钥格式，1：PKCS8(JAVA适用)，2：PKCS1(非JAVA适用), 数据库字段：key_format */
    @ApiDocField(description = "秘钥格式，1：PKCS8(JAVA适用)，2：PKCS1(非JAVA适用)", example = "1")
    @Min(value = 1, message = "秘钥格式错误")
    @Max(value = 2, message = "秘钥格式错误")
    private Byte keyFormat;

    /** 开发者生成的公钥, 数据库字段：public_key_isv */
    @ApiDocField(description = "开发者生成的公钥")
    private String publicKeyIsv;

    /** 开发者生成的私钥（交给开发者）, 数据库字段：private_key_isv */
    @ApiDocField(description = "开发者生成的私钥")
    private String privateKeyIsv;

    /** 平台生成的公钥（交给开发者）, 数据库字段：public_key_platform */
    @ApiDocField(description = "平台生成的公钥")
    private String publicKeyPlatform;

    /** 平台生成的私钥, 数据库字段：private_key_platform */
    @ApiDocField(description = "平台生成的私钥")
    private String privateKeyPlatform;
}
