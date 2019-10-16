package com.gitee.sop.servercommon.param;

/**
 * @author tanghc
 */
public interface ParamValidator {
    /**
     * 验证业务参数
     * @param obj
     */
    void validateBizParam(Object obj);
}
