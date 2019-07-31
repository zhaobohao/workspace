package com.gitee.easyopen.bean;

/**
 * @author tanghc
 */
public enum RequestMode {

    /**
     * 数字签名请求模式
     */
    SIGNATURE,
    /**
     * 公私钥加密模式，这样请求和返回的数据都经过加密处理
     */
    ENCRYPT

}
