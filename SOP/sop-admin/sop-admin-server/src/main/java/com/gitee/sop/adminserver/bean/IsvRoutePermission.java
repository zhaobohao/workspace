package com.gitee.sop.adminserver.bean;

import lombok.Data;

import java.util.List;

/**
 * isv授权过的路由
 * @author tanghc
 */
@Data
public class IsvRoutePermission {
    private String appKey;
    private List<String> routeIdList;
    private String routeIdListMd5;
    private String listenPath;
}
