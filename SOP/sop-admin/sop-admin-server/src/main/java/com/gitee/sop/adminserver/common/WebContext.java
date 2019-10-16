package com.gitee.sop.adminserver.common;

import com.gitee.sop.adminserver.entity.AdminUserInfo;

import javax.servlet.http.HttpSession;

import static com.gitee.easyopen.ApiContext.getSessionId;
import static com.gitee.easyopen.ApiContext.getSessionManager;

public class WebContext {
    private static WebContext INSTANCE = new WebContext();

    private static final String S_USER = "s_user";

    private WebContext() {
    }

    public static WebContext getInstance() {
        return INSTANCE;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public AdminUserInfo getLoginUser() {
        String sessionId = getSessionId();
        HttpSession session = getSessionManager().getSession(sessionId);
        if (session == null) {
            return null;
        }
        if (!session.getId().equals(sessionId)) {
            return null;
        }
        return (AdminUserInfo) session.getAttribute(S_USER);
    }

    public void setLoginUser(HttpSession session, AdminUserInfo user) {
        if (session != null) {
            session.setAttribute(S_USER, user);
        }
    }


}
