package com.gitee.easyopen;

/**
 * 负责签名相关
 * @author tanghc
 *
 */
public interface Signer {
    /**
     * 是否正确的签名
     * @param apiParam
     * @param secret
     * @param signMethod 签名方法 md5,rsa 
     * @return 返回true，表示签名正确
     */
    boolean isRightSign(ApiParam apiParam, String secret, String signMethod);
    
}
