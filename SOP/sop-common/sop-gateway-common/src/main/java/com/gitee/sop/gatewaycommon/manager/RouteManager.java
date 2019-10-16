package com.gitee.sop.gatewaycommon.manager;

/**
 * 管理各服务路由信息
 * @author tanghc
 */
@Deprecated
public interface RouteManager {

    /**
     * 刷新素有的微服务接口信息
     */
    void refresh();

}
