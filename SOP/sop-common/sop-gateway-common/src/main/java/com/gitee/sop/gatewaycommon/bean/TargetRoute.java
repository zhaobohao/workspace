package com.gitee.sop.gatewaycommon.bean;

/**
 * @author tanghc
 */
public interface TargetRoute<T> {

    /**
     * 返回服务信息
     *
     * @return 返回服务信息
     */
    ServiceRouteInfo getServiceRouteInfo();

    /**
     * 返回微服务路由对象
     *
     * @return 返回微服务路由对象
     */
    RouteDefinition getRouteDefinition();

    /**
     * 返回网关路由对象
     *
     * @return 返回网关路由对象
     */
    T getTargetRouteDefinition();
}
