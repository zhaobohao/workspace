package com.gitee.sop.gatewaycommon.loadbalancer.builder;

import com.gitee.sop.gatewaycommon.param.ApiParam;

/**
 * @author tanghc
 */
public interface GrayUserBuilder {

    /**
     * 获取灰度用户key
     *
     * @param apiParam apiParam
     * @return 返回用户key
     */
    String buildGrayUserKey(ApiParam apiParam);

    /**
     * 优先级，数字小优先
     *
     * @return 返回数字
     */
    int order();
}
