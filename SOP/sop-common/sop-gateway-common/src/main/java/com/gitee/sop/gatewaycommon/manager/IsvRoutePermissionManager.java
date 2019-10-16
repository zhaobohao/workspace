package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.BeanInitializer;
import com.gitee.sop.gatewaycommon.bean.IsvRoutePermission;

/**
 * @author tanghc
 */
public interface IsvRoutePermissionManager extends BeanInitializer {

    /**
     * 加载权限
     * @param isvRoutePermission isvRoutePermission
     */
    void update(IsvRoutePermission isvRoutePermission);

    /**
     * 判断是否有权限
     * @param appKey appKey
     * @param routeId 路由id
     * @return true：有
     */
    boolean hasPermission(String appKey, String routeId);

    /**
     * 删除权限
     * @param appKey appKey
     */
    void remove(String appKey);
}
