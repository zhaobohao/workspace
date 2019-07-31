package com.gitee.easyopen.verify;

import com.gitee.easyopen.ApiParam;

/**
 * @author tanghc
 */
public interface Verifier {

    /**
     * 校验
     * @param apiParam 接口参数
     * @param secret 秘钥
     * @return 返回校验结果，true：成功
     */
    boolean verify(ApiParam apiParam, String secret);
}
