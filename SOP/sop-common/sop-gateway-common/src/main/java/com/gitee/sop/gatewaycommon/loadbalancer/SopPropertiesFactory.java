package com.gitee.sop.gatewaycommon.loadbalancer;

import com.gitee.sop.gatewaycommon.manager.EnvironmentKeys;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;

/**
 * 自定义PropertiesFactory，用来动态添加LoadBalance规则
 * @author tanghc
 */
public class SopPropertiesFactory extends PropertiesFactory {

    /**
     * 配置文件配置：<serviceId>.ribbon.NFLoadBalancerRuleClassName=com.gitee.sop.gateway.loadbalancer.EnvironmentServerChooser
     * @param clazz
     * @param name serviceId
     * @return 返回class全限定名
     */
    @Override
    public String getClassName(Class clazz, String name) {
        if (clazz == IRule.class) {
            return EnvironmentKeys.ZUUL_CUSTOM_RULE_CLASSNAME.getValue();
        } else {
            return super.getClassName(clazz, name);
        }
    }
}
