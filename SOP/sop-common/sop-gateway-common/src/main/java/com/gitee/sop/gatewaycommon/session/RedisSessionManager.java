package com.gitee.sop.gatewaycommon.session;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * SessionManager的redis实现，使用redis管理session
 *
 * @author tanghc
 */
public class RedisSessionManager implements SessionManager {

    private ApiRedisTemplate redisTemplate;

    /**
     * 过期时间，30分钟
     */
    private int sessionTimeout = 30;
    /**
     * 存入redis中key的前缀
     */
    private String keyPrefix = "session:";

    public RedisSessionManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        Assert.notNull(redisTemplate, "RedisSessionManager中的redisTemplate不能为null");
        this.redisTemplate = new ApiRedisTemplate(redisTemplate.getConnectionFactory());
    }

    @Override
    public HttpSession getSession(String sessionId) {
        return this.getSession(sessionId, this.keyPrefix);
    }

    public HttpSession getSession(String sessionId, String keyPrefix) {
        if (this.hasKey(sessionId)) {
            return RedisHttpSession.createExistSession(sessionId, getServletContext(), redisTemplate, keyPrefix);
        } else {
            sessionId = this.buildSessionId(sessionId);
            return RedisHttpSession.createNewSession(getServletContext(), sessionId, this.getSessionTimeout(),
                    redisTemplate, keyPrefix);
        }
    }

    /**
     * 构建sessionId
     *
     * @param id
     * @return 返回sessionid
     */
    public String buildSessionId(String id) {
        return (id != null ? id : UUID.randomUUID().toString().replace("-", "").toUpperCase());
    }

    public boolean hasKey(String sessionId) {
        if (sessionId == null) {
            return false;
        } else {
            String key = RedisHttpSession.buildKey(this.keyPrefix, sessionId);
            return redisTemplate.hasKey(key);
        }
    }

    public ServletContext getServletContext() {
        return null;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     * 设置session过期时间，单位分钟
     *
     * @param sessionTimeout
     */
    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public ApiRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(ApiRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * 设置存入redis中key的前缀，默认为"session:"
     *
     * @param keyPrefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
