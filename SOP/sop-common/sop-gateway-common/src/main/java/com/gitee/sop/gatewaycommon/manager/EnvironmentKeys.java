package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.loadbalancer.EnvironmentServerChooser;

public enum EnvironmentKeys {
    SPRING_PROFILES_ACTIVE("spring.profiles.active", "default"),
    /**
     * spring.application.name
     */
    SPRING_APPLICATION_NAME("spring.application.name"),
    /**
     * 指定负载均衡规则类，默认使用com.gitee.sop.gateway.loadbalancer.PreEnvironmentServerChooser
     */
    ZUUL_CUSTOM_RULE_CLASSNAME("zuul.custom-rule-classname", EnvironmentServerChooser.class.getName()),

    /**
     * sign.urlencode=true，签名验证拼接字符串的value部分进行urlencode
     */
    SIGN_URLENCODE("sign.urlencode"),

    /**
     * sop.restful.enable=true，开启传统web开发模式
     */
    SOP_RESTFUL_ENABLE("sop.restful.enable");

    private String key;
    private String defaultValue;

    public String getKey() {
        return key;
    }

    EnvironmentKeys(String key) {
        this.key = key;
    }

    EnvironmentKeys(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return EnvironmentContext.getValue(key, defaultValue);
    }
}