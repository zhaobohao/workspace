package com.gitee.easyopen.permission;

import java.util.List;

import com.gitee.easyopen.ManagerInitializer;

/**
 * 权限管理定义
 * @author tanghc
 */
public interface PermissionManager extends ManagerInitializer {

    /**
     * 获取客户端拥有的接口
     * 
     * @param appKey
     * @return 返回接口列表
     */
    List<String> listClientApi(String appKey);

    /**
     * 能否访问
     * @param appKey appKey
     * @param name 接口名
     * @param version 版本号
     * @return true：能
     */
    boolean canVisit(String appKey,String name,String version);

    /**
     * 加载权限配置
     */
    void loadPermissionConfig();

    /**
     * 加载权限配置到缓存
     * @param configJson
     */
    void loadPermissionCache(String configJson);
}
