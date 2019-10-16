package com.gitee.sop.gatewaycommon.validate;

import com.gitee.sop.gatewaycommon.param.ApiParam;

/**
 * 校验接口
 * 
 * @author tanghc
 *
 */
public interface Validator {
    /**
     * 接口验证
     * @param param 接口参数
     */
    void validate(ApiParam param);
    
}
