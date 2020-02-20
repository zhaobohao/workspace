package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.ConfigLimitDto;
import com.gitee.sop.gatewaycommon.bean.ServiceBeanInitializer;

/**
 * @author tanghc
 */
public interface LimitConfigManager extends ServiceBeanInitializer {
    /**
     * 更新限流配置
     * @param configLimitDto 路由配置
     */
    void update(ConfigLimitDto configLimitDto);

    /**
     * 获取限流配置
     * @param limitKey 路由id
     * @return 返回ConfigLimitDto
     */
    ConfigLimitDto get(String limitKey);
}
