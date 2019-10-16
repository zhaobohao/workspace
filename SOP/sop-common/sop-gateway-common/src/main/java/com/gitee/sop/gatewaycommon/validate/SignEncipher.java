package com.gitee.sop.gatewaycommon.validate;

/**
 * @author tanghc
 */
public interface SignEncipher {
    /**
     * 签名的摘要算法
     * @param input 待签名数据
     * @param secret 秘钥
     * @return 返回加密后的数据
     */
    byte[] encrypt(String input, String secret);
}
