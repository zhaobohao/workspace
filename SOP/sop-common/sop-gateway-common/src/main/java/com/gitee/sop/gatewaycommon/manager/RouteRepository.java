package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.TargetRoute;

import java.util.Collection;

/**
 * @author tanghc
 */
public interface RouteRepository<T extends TargetRoute> {
    /**
     * 获取路由信息
     * @param id 路由id
     * @return 返回路由信息，找不到返回null
     */
    T get(String id);

    /**
     * 返回所有路由信息
     * @return 返回所有路由信息
     */
    Collection<T> getAll();

    /**
     * 添加路由
     * @param targetRoute 模板路由对象
     * @return 返回路由id
     */
    String add(T targetRoute);

    /**
     * 更新路由
     * @param targetRoute 模板路由对象
     */
    void update(T targetRoute);

    /**
     * 删除路由
     * @param id 路由id
     */
    void delete(String id);

    /**
     * 删除service下的所有路由
     * @param serviceId 服务id
     */
    void deleteAll(String serviceId);

    /**
     * 刷新
     */
    default void refresh() {}
}
