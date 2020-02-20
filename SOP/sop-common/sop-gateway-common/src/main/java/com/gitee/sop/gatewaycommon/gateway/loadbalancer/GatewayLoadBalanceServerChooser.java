package com.gitee.sop.gatewaycommon.gateway.loadbalancer;

import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.loadbalancer.LoadBalanceServerChooser;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author tanghc
 */
public class GatewayLoadBalanceServerChooser extends LoadBalanceServerChooser<ServerWebExchange, ServiceInstance> {

    public GatewayLoadBalanceServerChooser(SpringClientFactory clientFactory) {
        this.setClientFactory(clientFactory);
    }

    @Override
    public String getHost(ServerWebExchange exchange) {
        return exchange.getRequest().getURI().getHost();
    }

    @Override
    public ApiParam getApiParam(ServerWebExchange exchange) {
        return ServerWebExchangeUtil.getApiParam(exchange);
    }

}
