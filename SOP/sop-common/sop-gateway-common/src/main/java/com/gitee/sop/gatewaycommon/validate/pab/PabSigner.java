package com.gitee.sop.gatewaycommon.validate.pab;

import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.validate.Signer;

/**
 * 平安外联组签名验证实现。
 *
 * @author zhaobo
 */
public class PabSigner implements Signer {

    @Override
    public boolean checkSign(ApiParam apiParam, String secret) {
        // 服务端存的是公钥
        String publicKeyISV = secret;
        String charset = apiParam.fetchCharset();
        String signType = apiParam.fetchSignMethod();
        if (signType == null) {
            throw ErrorEnum.ISV_DECRYPTION_ERROR_MISSING_ENCRYPT_TYPE.getErrorMeta().getException();
        }
        if (charset == null) {
            throw ErrorEnum.ISV_INVALID_CHARSET.getErrorMeta().getException();
        }
        return PabSignature.rsaCheckV2BySignType(apiParam, publicKeyISV, charset, signType);
    }

}
