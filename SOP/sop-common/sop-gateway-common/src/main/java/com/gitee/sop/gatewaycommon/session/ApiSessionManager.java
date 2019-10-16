package com.gitee.sop.gatewaycommon.session;

import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * session管理,默认存放的是{@link ApiHttpSession}。采用谷歌guava缓存实现。
 *
 * @author tanghc
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
        if (sessionId == null) {
            return this.createSession(sessionId);
        }
        try {
            return cache.get(sessionId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ErrorEnum.ISP_UNKNOWN_ERROR.getErrorMeta().getException();
        }
    }

    /**
     * 创建一个session
     *
     * @param sessionId 传null将返回一个新session
     * @return 返回session
     */
    protected HttpSession createSession(String sessionId) {
        ServletContext servletContext = null;
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
