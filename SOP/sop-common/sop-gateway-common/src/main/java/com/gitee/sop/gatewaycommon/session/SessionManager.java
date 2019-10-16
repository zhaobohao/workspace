package com.gitee.sop.gatewaycommon.session;

import javax.servlet.http.HttpSession;

/**
 * session管理
 * 
 * @author tanghc
 *
 */
public interface SessionManager {

    /**
     * 根据sessionId获取session
     * 
     * @param sessionId 客户端传过来的sessionId,为null时创建一个新session
     * @return 返回session
     */
    HttpSession getSession(String sessionId);
}
