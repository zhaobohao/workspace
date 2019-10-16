package com.gitee.sop.gatewaycommon.gateway.param;

import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.BaseParamBuilder;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author tanghc
 */
public class GatewayParamBuilder extends BaseParamBuilder<ServerWebExchange> {

    @Override
    public ApiParam buildRequestParams(ServerWebExchange exchange) {
        return ServerWebExchangeUtil.getRequestParams(exchange);
    }

    @Override
    public String getIP(ServerWebExchange ctx) {
        return ctx.getRequest().getRemoteAddress().getAddress().getHostAddress();
    }

    @Override
    public void setVersionInHeader(ServerWebExchange ctx, String headerName, String version) {
        ctx.getRequest().getHeaders().add(headerName, version);
    }
}
