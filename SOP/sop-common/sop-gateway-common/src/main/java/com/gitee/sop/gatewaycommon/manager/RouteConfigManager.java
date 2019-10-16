package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.BeanInitializer;
import com.gitee.sop.gatewaycommon.bean.RouteConfig;

/**
 * 路由配置管理
 * @author tanghc
 */
public interface RouteConfigManager extends BeanInitializer {
    /**
     * 更新路由配置
     * @param routeConfig 路由配置
     */
    void update(RouteConfig routeConfig);

    /**
     * 获取路由配置
     * @param routeId 路由id
     * @return 返回RouteConfig
     */
    RouteConfig get(String routeId);
}
