package com.gitee.sop.gatewaycommon.route;

import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;

/**
 * 新的实例注册事件
 *
 * @author tanghc
 */
public interface RegistryEvent {

    /**
     * 新实例注册进来时触发
     * @param instanceDefinition 实例信息
     */
    void onRegistry(InstanceDefinition instanceDefinition);

    /**
     * 服务下线时触发
     * @param serviceId 服务id
     */
    void onRemove(String serviceId);
}