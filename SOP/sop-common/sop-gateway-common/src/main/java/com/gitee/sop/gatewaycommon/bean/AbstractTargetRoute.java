package com.gitee.sop.gatewaycommon.bean;

/**
 * @author tanghc
 */
public abstract class AbstractTargetRoute<T> implements TargetRoute<T> {

    private ServiceRouteInfo serviceRouteInfo;
    private RouteDefinition routeDefinition;
    private T targetRoute;

    public AbstractTargetRoute(ServiceRouteInfo serviceRouteInfo, RouteDefinition routeDefinition, T targetRoute) {
        this.serviceRouteInfo = serviceRouteInfo;
        this.routeDefinition = routeDefinition;
        this.targetRoute = targetRoute;
    }

    @Override
    public ServiceRouteInfo getServiceRouteInfo() {
        return serviceRouteInfo;
    }

    @Override
    public RouteDefinition getRouteDefinition() {
        return routeDefinition;
    }

    @Override
    public T getTargetRouteDefinition() {
        return targetRoute;
    }
}
