package com.gitee.easyopen;

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
    
    /**
     * 验证业务参数
     * 
     * @param obj 业务参数
     */
    void validateBusiParam(Object obj);
}
