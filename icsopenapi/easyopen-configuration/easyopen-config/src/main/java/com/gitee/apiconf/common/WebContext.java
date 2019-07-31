package com.gitee.apiconf.common;

import com.gitee.apiconf.entity.AdminUser;
import com.gitee.easyopen.ApiContext;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class WebContext {
    private static WebContext INSTANCE = new WebContext();

    /** jwt过期时间（分钟） */
    private static final int JWT_TIMEOUT_MINUTES = 20;

    private LoadingCache<String, Integer> jwtTimeoutManager =
            CacheBuilder.newBuilder()
                    .expireAfterWrite(JWT_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                    .build(new CacheLoader<String, Integer>() {
                        @Override
                        public Integer load(String key) throws Exception {
                            return 0;
                        }
                    });

    private static final String S_USER = "s_user";

    private WebContext() {
    }

    public static WebContext getInstance() {
        return INSTANCE;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 等同session.setAttribute(key, value);
     *
     * @param key
     * @param value
     */
    public void setSessionAttr(String key, Object value) {
        this.getSession().setAttribute(key, value);
    }

    /**
     * 等同session.getAttribute(key);
     *
     * @param key
     * @return
     */
    public Object getSessionAttr(String key) {
        return this.getSession().getAttribute(key);
    }

    public HttpSession getSession() {
        if (getRequest() != null) {
            return getRequest().getSession();
        }
        return null;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public AdminUser getLoginUser() {
        return (AdminUser) this.getSession().getAttribute(S_USER);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public AdminUser getAccessUser() {
        HttpSession session = ApiContext.getManagedSession();
        if (session == null) {
            return null;
        }
        return (AdminUser) session.getAttribute(S_USER);
    }


    /**
     * 保存当前用户
     *
     * @param session
     * @param user
     */
    public void setAccessUser(HttpSession session, AdminUser user) {
        if (session != null) {
            session.setAttribute(S_USER, user);
        }
    }

    /**
     * 保存jwt用户
     * @param user
     */
    public void setJwtUser(AdminUser user) {
        jwtTimeoutManager.put(String.valueOf(user.getId()), JWT_TIMEOUT_MINUTES);
    }

    /**
     * jwt是否过期
     * @param id
     * @return
     */
    public boolean isJwtTimeout(String id) {
        try {
            return jwtTimeoutManager.get(id) == 0;
        } catch (ExecutionException e) {
            return true;
        }
    }

    public void refreshJwt(String id) {
        jwtTimeoutManager.put(id, JWT_TIMEOUT_MINUTES);
    }

}
