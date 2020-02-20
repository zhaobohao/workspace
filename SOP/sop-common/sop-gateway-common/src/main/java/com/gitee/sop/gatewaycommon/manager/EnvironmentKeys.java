package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.zuul.loadbalancer.EnvironmentServerChooser;

public enum EnvironmentKeys {
    SPRING_PROFILES_ACTIVE("spring.profiles.active", "default"),
    /**
     * spring.application.name
     */
    SPRING_APPLICATION_NAME("spring.application.name"),
    /**
     * 指定负载均衡规则类
     */
    ZUUL_CUSTOM_RULE_CLASSNAME("zuul.custom-rule-classname", EnvironmentServerChooser.class.getName()),

    /**
     * sign.urlencode=true，签名验证拼接字符串的value部分进行urlencode
     */
    SIGN_URLENCODE("sign.urlencode"),

    /**
     * sop.restful.enable=true，开启传统web开发模式
     */
    SOP_RESTFUL_ENABLE("sop.restful.enable"),

    /**
     * sop.restful.path=/xx ，指定请求前缀，默认/rest
     */
    SOP_RESTFUL_PATH("sop.restful.path", "/rest"),

    /**
     * 排除其它微服务，多个用英文逗号隔开
     */
    SOP_SERVICE_EXCLUDE("sop.service.exclude"),
    /**
     * 排除其它微服务，正则形式，多个用英文逗号隔开
     */
    SOP_SERVICE_EXCLUDE_REGEX("sop.service.exclude-regex"),
    /**
     * 预发布域名
     */
    PRE_DOMAIN("pre.domain");

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

    public String getValue(String defaultValue) {
        return EnvironmentContext.getValue(key, defaultValue);
    }
}