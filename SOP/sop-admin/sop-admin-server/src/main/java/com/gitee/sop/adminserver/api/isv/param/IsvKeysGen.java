package com.gitee.sop.adminserver.api.isv.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author tanghc
 */
@Data
public class IsvKeysGen {
    @ApiDocField(description = "秘钥格式，1：PKCS8(JAVA适用)，2：PKCS1(非JAVA适用)", example = "1")
    @Min(value = 1, message = "秘钥格式错误")
    @Max(value = 2, message = "秘钥格式错误")
    private Byte keyFormat;
}
