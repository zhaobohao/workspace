package com.gitee.sop.gatewaycommon.bean;

import com.gitee.sop.gatewaycommon.param.ApiParam;

/**
 * @author tanghc
 */
public interface ApiParamAware<T> {
    ApiParam getApiParam(T t);
}
