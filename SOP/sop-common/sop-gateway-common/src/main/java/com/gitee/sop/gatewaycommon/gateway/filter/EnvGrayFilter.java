package com.gitee.sop.gatewaycommon.gateway.filter;

import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.manager.EnvGrayManager;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author tanghc
 * @deprecated
 * @see com.gitee.sop.gatewaycommon.gateway.route.GatewayForwardChooser
 */
@Deprecated
public class EnvGrayFilter implements GlobalFilter, Ordered {

    @Autowired
    private EnvGrayManager envGrayManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ApiParam apiParam = ServerWebExchangeUtil.getApiParam(exchange);
        String nameVersion = apiParam.fetchNameVersion();
        TargetRoute targetRoute = RouteRepositoryContext.getRouteRepository().get(nameVersion);
        if (targetRoute == null) {
            return chain.filter(exchange);
        }
        String serviceId = targetRoute.getServiceRouteInfo().fetchServiceIdLowerCase();
        // 如果服务在灰度阶段，返回一个灰度版本号
        String version = envGrayManager.getVersion(serviceId, nameVersion);
        if (version != null && envGrayManager.containsKey(serviceId, apiParam.fetchAppKey())) {
            ServerWebExchange serverWebExchange = ServerWebExchangeUtil.addHeaders(exchange, httpHeaders -> httpHeaders.set(ParamNames.HEADER_VERSION_NAME, version));
            return chain.filter(serverWebExchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Orders.ENV_GRAY_FILTER_ORDER;
    }
}
