package com.gitee.sop.gatewaycommon.gateway.route;

import com.gitee.sop.gatewaycommon.bean.AbstractTargetRoute;
import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.ServiceRouteInfo;

/**
 * @author tanghc
 */
public class GatewayTargetRoute extends AbstractTargetRoute<org.springframework.cloud.gateway.route.RouteDefinition> {

    public GatewayTargetRoute(ServiceRouteInfo serviceRouteInfo, RouteDefinition routeDefinition, org.springframework.cloud.gateway.route.RouteDefinition targetRoute) {
        super(serviceRouteInfo, routeDefinition, targetRoute);
    }
}
