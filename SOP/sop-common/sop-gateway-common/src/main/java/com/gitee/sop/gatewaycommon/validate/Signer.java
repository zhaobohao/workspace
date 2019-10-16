package com.gitee.sop.gatewaycommon.validate;

import com.gitee.sop.gatewaycommon.param.ApiParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 负责签名校验
 * @author tanghc
 *
 */
public interface Signer {

    /**
     * 签名校验
     * @param apiParam 参数
     * @param secret 秘钥
     * @return true签名正确
     */
    boolean checkSign(ApiParam apiParam, String secret);
    
}
