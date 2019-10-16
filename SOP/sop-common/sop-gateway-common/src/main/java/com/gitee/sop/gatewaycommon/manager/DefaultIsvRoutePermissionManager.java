package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.IsvRoutePermission;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanghc
 */
public class DefaultIsvRoutePermissionManager implements IsvRoutePermissionManager {

    /** key: appKey */
    protected static Map<String, IsvRoutePermission> isvRoutePermissionMap = new ConcurrentHashMap<>(64);

    @Override
    public void load() {

    }

    @Override
    public void update(IsvRoutePermission isvRoutePermission) {
        IsvRoutePermission routePermission = isvRoutePermissionMap.get(isvRoutePermission.getAppKey());
        if (routePermission == null) {
            isvRoutePermissionMap.put(isvRoutePermission.getAppKey(), isvRoutePermission);
        } else {
            if (!StringUtils.equals(isvRoutePermission.getRouteIdListMd5(), routePermission.getRouteIdListMd5())) {
                isvRoutePermissionMap.put(isvRoutePermission.getAppKey(), isvRoutePermission);
            }
        }
    }

    @Override
    public boolean hasPermission(String appKey, String routeId) {
        IsvRoutePermission routePermission = isvRoutePermissionMap.get(appKey);
        if (routePermission == null) {
            return false;
        }
        return routePermission.getRouteIdList().contains(routeId);
    }

    @Override
    public void remove(String appKey) {
        isvRoutePermissionMap.remove(appKey);
    }
}
