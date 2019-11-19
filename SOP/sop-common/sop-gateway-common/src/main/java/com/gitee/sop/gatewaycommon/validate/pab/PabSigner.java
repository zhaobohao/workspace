package com.gitee.sop.gatewaycommon.validate.pab;

import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.validate.Signer;

/**
 * 平安外联组签名验证实现。
 *  做为银行方或者渠道方，手里会有对方的公钥和自己的私钥。
 *  在做data加密的时候用公钥。加签的时候用自己的私钥。
 *
 *  同理，当对方的报文过来的时候，data解密用自己的私钥。
 *  在验签的时候，用对方的公钥。
 * @author zhaobo
 */
public class PabSigner implements Signer {

    @Override
    public boolean checkSign(ApiParam apiParam, String publicKeyISV) {
        // 服务端存的是公钥
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
