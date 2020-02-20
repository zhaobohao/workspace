package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.TargetRoute;

/**
 * @author tanghc
 */
public class RouteRepositoryContext {

    private RouteRepositoryContext() {
    }

    private static RouteRepository<? extends TargetRoute> routeRepository;

    public static RouteRepository<? extends TargetRoute> getRouteRepository() {
        return routeRepository;
    }

    public static <T extends TargetRoute> void setRouteRepository(RouteRepository<T> routeRepository) {
        RouteRepositoryContext.routeRepository = routeRepository;
    }

    /**
     * 根据路由id查询路由
     *
     * @param routeId 路由id
     * @return 返回路由信息，没有返回null
     */
    public static TargetRoute getTargetRoute(String routeId) {
        return routeRepository.get(routeId);
    }

}
