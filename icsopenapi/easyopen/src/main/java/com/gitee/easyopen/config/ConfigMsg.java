package com.gitee.easyopen.config;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * netty消息体
 * @author tanghc
 */
public class ConfigMsg implements Serializable {
    private static final long serialVersionUID = -8663237383025602418L;

    private String app;
    private String code;
    private String data;
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
    
    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
