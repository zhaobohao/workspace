package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.BeanInitializer;
import com.gitee.sop.gatewaycommon.zuul.loadbalancer.ServiceGrayConfig;

/**
 * @author tanghc
 */
public interface EnvGrayManager extends BeanInitializer {

    String ENV_GRAY = "sop.env-gray";

    /**
     * 保存灰度配置
     * @param serviceGrayConfig 灰度配置
     */
    void saveServiceGrayConfig(ServiceGrayConfig serviceGrayConfig);

    /**
     * 实例是否允许
     * @param serviceId serviceId
     * @param userKey 用户key，如appKey
     * @return true：允许访问
     */
    boolean containsKey(String serviceId, Object userKey);

    /**
     * 获取灰度发布新版本号
     * @param serviceId serviceId
     * @param nameVersion 路由id
     * @return 返回新版本号
     */
    String getVersion(String serviceId, String nameVersion);

    /**
     * 开启灰度
     * @param instanceId instanceId
     * @param serviceId serviceId
     */
    void openGray(String instanceId, String serviceId);

    /**
     * 关闭灰度
     * @param instanceId instanceId
     */
    void closeGray(String instanceId);
}
