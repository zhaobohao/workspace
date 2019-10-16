package com.gitee.sop.gatewaycommon.route;

import org.springframework.context.ApplicationEvent;

/**
 * 发现新服务，更新路由信息
 *
 * @author tanghc
 */
public interface RegistryListener {

    void onEvent(ApplicationEvent applicationEvent);

}
