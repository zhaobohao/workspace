package com.gitee.sop.gatewaycommon.gateway.controller;

import com.gitee.sop.gatewaycommon.bean.BaseErrorLogController;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @author tanghc
 */
@RestController
public class ErrorLogController extends BaseErrorLogController<ServerWebExchange> {

    @Override
    protected ApiParam getApiParam(ServerWebExchange request) {
        Map<String, String> params = request.getRequest().getQueryParams().toSingleValueMap();
        return ApiParam.build(params);
    }
}
