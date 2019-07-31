package com.gitee.easyopen.register;

import com.gitee.easyopen.ApiConfig;

/**
 * @author tanghc
 */
public interface RegistCallback {

    /**
     * 接口注册完毕后出发
     * @param apiConfig
     */
    void onRegistFinished(ApiConfig apiConfig);
    
}
