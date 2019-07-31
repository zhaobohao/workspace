package com.gitee.easyopen.permission;

import java.io.Serializable;

/**
 * @author tanghc
 */
public class ApiInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String app_key;
    private String name;
    private String version;

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
