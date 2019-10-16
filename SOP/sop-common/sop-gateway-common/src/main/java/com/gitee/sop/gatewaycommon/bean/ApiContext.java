package com.gitee.sop.gatewaycommon.bean;

import com.gitee.sop.gatewaycommon.session.SessionManager;

/**
 * 应用上下文,方便获取信息
 *
 * @author tanghc
 */
public class ApiContext {

    /**
     * 获取session管理器
     *
     * @return 返回SessionManager
     */
    public static SessionManager getSessionManager() {
        return getApiConfig().getSessionManager();
    }


    public static ApiConfig getApiConfig() {
        return ApiConfig.getInstance();
    }

    public static void setApiConfig(ApiConfig apiConfig) {
        ApiConfig.setInstance(apiConfig);
    }

}
