package com.gitee.easyopen.session;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.exception.ApiException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * session管理,默认存放的是{@link ApiHttpSession}。采用谷歌guava缓存实现。
 * 
 * @author tanghc
 *
 */
public class ApiSessionManager implements SessionManager {
    private static Logger logger = LoggerFactory.getLogger(ApiSessionManager.class);

    private int sessionTimeout = 20;

    private LoadingCache<String, HttpSession> cache;

    public ApiSessionManager() {
        cache = this.buildCache();
    }

    @Override
    public HttpSession getSession(String sessionId) {
        if(sessionId == null) {
            return this.createSession(sessionId);
        }
        try {
            HttpSession session = cache.get(sessionId);
            return session;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApiException("create session error");
        }
    }

    /**
     * 创建一个session
     * 
     * @param sessionId 传null将返回一个新session
     * @return 返回session
     */
    protected HttpSession createSession(String sessionId) {
        ServletContext servletContext = getServletContext();
        HttpSession session = this.newSession(sessionId, servletContext);
        session.setMaxInactiveInterval(getSessionTimeout());
        this.cache.put(session.getId(), session);
        return session;
    }

    /**
     * 返回新的session实例
     * 
     * @param sessionId
     * @param servletContext
     * @return 返回session
     */
    protected HttpSession newSession(String sessionId, ServletContext servletContext) {
        return new ApiHttpSession(servletContext, sessionId);
    }

    protected ServletContext getServletContext() {
        return ApiContext.getServletContext();
    }

    protected LoadingCache<String, HttpSession> buildCache() {
        return CacheBuilder.newBuilder().expireAfterAccess(getSessionTimeout(), TimeUnit.MINUTES)
                .build(new CacheLoader<String, HttpSession>() {
                    // 找不到sessionId对应的HttpSession时,进入这个方法
                    // 找不到就新建一个
                    @Override
                    public HttpSession load(String sessionId) throws Exception {
                        return createSession(sessionId);
                    }
                });
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    /**
     * 过期时间,分钟,默认20分钟
     * 
     * @return 返回过期时间
     */
    public int getSessionTimeout() {
        return sessionTimeout;
    }

}
