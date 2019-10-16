package com.gitee.sop.gatewaycommon.param;

/**
 * @author tanghc
 */
public interface ParamBuilder<T> {
    /**
     * 从request提取参数
     * @param request
     * @return 返回ApiParam
     * @throws Exception
     */
    ApiParam build(T request);
}
