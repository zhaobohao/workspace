package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;

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
     * 检查路由是否存在，不存在报错
     * @param routeId 路由id
     * @param errorEnum 报错信息
     */
    public static void checkExist(String routeId, ErrorEnum errorEnum) {
        TargetRoute targetRoute = routeRepository.get(routeId);
        if (targetRoute == null) {
            throw errorEnum.getErrorMeta().getException();
        }
    }

}
