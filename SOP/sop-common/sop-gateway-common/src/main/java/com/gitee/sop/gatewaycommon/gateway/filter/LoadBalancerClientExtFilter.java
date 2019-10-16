package com.gitee.sop.gatewaycommon.gateway.filter;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.util.RouteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.filter.LoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * 在LoadBalancerClientFilter后面处理，从Route中找到具体的path，然后插入到uri的path中
 *
 * @author tanghc
 */
@Slf4j
public class LoadBalancerClientExtFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        String path = this.findPath(exchange, route);
        if (StringUtils.hasLength(path)) {
            URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(url);
            uriComponentsBuilder.path(path);
            URI requestUrl = uriComponentsBuilder.build(true).toUri();
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, requestUrl);
        }
        return chain.filter(exchange);
    }

    protected String findPath(ServerWebExchange exchange, Route route) {
        String path = exchange.getAttribute(SopConstants.REDIRECT_PATH_KEY);
        if (path != null) {
            return path;
        }
        URI routeUri = route.getUri();
        String uriStr = routeUri.toString();
        return RouteUtil.findPath(uriStr);
    }

}
