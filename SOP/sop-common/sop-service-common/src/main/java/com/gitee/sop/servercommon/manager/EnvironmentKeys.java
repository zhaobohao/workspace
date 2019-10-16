package com.gitee.sop.servercommon.manager;

import javax.annotation.Nullable;

public enum EnvironmentKeys {
    SPRING_PROFILES_ACTIVE("spring.profiles.active", "default"),
    /**
     * spring.application.name
     */
    SPRING_APPLICATION_NAME("spring.application.name"),

    /**
     * sop.restful.prefix=xxx，指定web开发模式前缀，不指定默认为service-id
     */
    SOP_RESTFUL_PREFIX("sop.restful.prefix"),

    /**
     * sop.restful.old-model=true，兼容老的restful调用方式（2.4.1之前）
     */
    SOP_RESTFUL_OLD_MODEL("sop.restful.old-model", "false");

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

    @Nullable
    public String getValue() {
        return EnvironmentContext.getValue(key, defaultValue);
    }
}