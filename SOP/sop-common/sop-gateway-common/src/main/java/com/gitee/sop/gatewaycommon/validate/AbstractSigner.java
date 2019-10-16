package com.gitee.sop.gatewaycommon.validate;

import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tanghc
 */
@Slf4j
public abstract class AbstractSigner implements Signer {

    /**
     * 构建服务端签名串
     *
     * @param params 接口参数
     * @param secret 秘钥
     * @return 返回服务端签名串
     */
    protected abstract String buildServerSign(ApiParam params, String secret);

    @Override
    public boolean checkSign(ApiParam apiParam, String secret) {
        String clientSign = apiParam.fetchSignAndRemove();
        if (StringUtils.isBlank(clientSign)) {
            throw ErrorEnum.ISV_MISSING_SIGNATURE.getErrorMeta().getException();
        }
        String serverSign = buildServerSign(apiParam, secret);
        return clientSign.equals(serverSign);
    }

    protected static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }
}
