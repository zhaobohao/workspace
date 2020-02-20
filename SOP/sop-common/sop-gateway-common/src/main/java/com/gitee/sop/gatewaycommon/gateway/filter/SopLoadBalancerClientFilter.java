package com.gitee.sop.gatewaycommon.gateway.filter;

import com.gitee.sop.gatewaycommon.gateway.loadbalancer.SopLoadBalancerClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 扩展负载均衡过滤器
 * @author tanghc
 */
public class SopLoadBalancerClientFilter extends LoadBalancerClientFilter {
    public SopLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        if (loadBalancer instanceof SopLoadBalancerClient) {
            SopLoadBalancerClient sopLoadBalancerClient = (SopLoadBalancerClient)loadBalancer;
            return sopLoadBalancerClient.choose(((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost(), exchange);
        } else {
            return super.choose(exchange);
        }
    }
}
