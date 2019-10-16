package com.gitee.sop.gatewaycommon.route;

import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;
import com.gitee.sop.gatewaycommon.bean.ServiceRouteInfo;

/**
 * @author tanghc
 */
public interface RoutesProcessor {
    /**
     * 删除serviceId下所有路由
     *
     * @param serviceId serviceId
     */
    void removeAllRoutes(String serviceId);

    /**
     * 保存路由
     *
     * @param serviceRouteInfo 路由信息
     * @param instance 服务实例
     */
    void saveRoutes(ServiceRouteInfo serviceRouteInfo, InstanceDefinition instance);
}
