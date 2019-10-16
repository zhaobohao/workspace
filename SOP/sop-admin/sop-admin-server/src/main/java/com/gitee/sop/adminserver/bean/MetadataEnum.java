package com.gitee.sop.adminserver.bean;

/**
 * @author tanghc
 */
public enum MetadataEnum {
    /**
     * 预发布环境
     */
    ENV_PRE("env", "pre"),

    /**
     * 上线环境
     */
    ENV_ONLINE("env", ""),

    /**
     * 灰度环境
     */
    ENV_GRAY("env", "gray"),
    ;
    private String key,value;

    MetadataEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
