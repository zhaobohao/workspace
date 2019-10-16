package com.gitee.sop.gatewaycommon.validate.alipay;

import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.validate.Signer;

/**
 * 支付宝签名验证实现。
 *
 * @author tanghc
 * @see <a href="https://docs.open.alipay.com/291/106118">支付宝签名</a>
 */
public class AlipaySigner implements Signer {

    @Override
    public boolean checkSign(ApiParam apiParam, String secret) {
        // 服务端存的是公钥
        String publicKey = secret;
        String charset = apiParam.fetchCharset();
        String signType = apiParam.fetchSignMethod();
        if (signType == null) {
            throw ErrorEnum.ISV_DECRYPTION_ERROR_MISSING_ENCRYPT_TYPE.getErrorMeta().getException();
        }
        if (charset == null) {
            throw ErrorEnum.ISV_INVALID_CHARSET.getErrorMeta().getException();
        }
        return AlipaySignature.rsaCheckV2(apiParam, publicKey, charset, signType);
    }

}
