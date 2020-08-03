package com.dc3.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Aes/Rsa 加密密钥
 *
 * @author pnoker
 */
public class Keys {

    /**
     * Aes 密钥
     */
    @Data
    @AllArgsConstructor
    public class Aes {
        private String privateKey;
    }

    /**
     * RSA 密钥对
     */
    @Data
    @AllArgsConstructor
    public class Rsa {
        private String publicKey;
        private String privateKey;
    }
}