package com.gitee.sop.gatewaycommon.validate;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * HMAC_MD5加密
 * @author tanghc
 */
@Slf4j
public class SignEncipherHMAC_MD5 implements SignEncipher {

    public static final String HMAC_MD5 = "HmacMD5";

    @Override
    public byte[] encrypt(String input, String secret) {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(SopConstants.CHARSET_UTF8), HMAC_MD5);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return mac.doFinal(input.getBytes(SopConstants.CHARSET_UTF8));
        } catch (NoSuchAlgorithmException e) {
            log.error("HMAC_MD5加密加密失败NoSuchAlgorithmException", e);
            throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException();
        } catch (InvalidKeyException e) {
            log.error("HMAC_MD5加密加密失败InvalidKeyException", e);
            throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException();
        }
    }
}
