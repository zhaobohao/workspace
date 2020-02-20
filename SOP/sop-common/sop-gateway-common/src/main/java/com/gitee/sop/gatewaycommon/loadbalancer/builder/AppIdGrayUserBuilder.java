package com.gitee.sop.gatewaycommon.loadbalancer.builder;

import com.gitee.sop.gatewaycommon.param.ApiParam;

/**
 * @author tanghc
 */
public class AppIdGrayUserBuilder implements GrayUserBuilder {

    @Override
    public String buildGrayUserKey(ApiParam apiParam) {
        return apiParam.fetchAppKey();
    }

    @Override
    public int order() {
        return 0;
    }
}
