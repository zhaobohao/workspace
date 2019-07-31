package com.gitee.easyopen.session;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * RedisHttpSession
 * @author tanghc
 *
 */
@SuppressWarnings("deprecation")
public class RedisHttpSession implements HttpSession ,Serializable {
    private static final long serialVersionUID = -8081963657251144855L;

    private static final int SEC60 = 60;

    private static final String SESSION_ATTR = "session_attr:";
    private static final String CREATION_TIME = "creationTime";
    private static final String LAST_ACCESSED_TIME = "lastAccessedTime";
    private static final String MAX_INACTIVE_INTERVAL = "maxInactiveInterval";

    /** 存入redis的key */
    private String key;

    /** sessionId */
    private String id;

    private ServletContext servletContext;

    private RedisTemplate redisTemplate;

    private RedisHttpSession(){}


    protected static String buildId(String id) {
        return (id != null ? id : UUID.randomUUID().toString().replace("-", "").toUpperCase());
    }

    public static String buildKey(String keyPrefix, String sessionId) {
        Assert.notNull(keyPrefix, "sessionPrefix不能为null");
        return keyPrefix + sessionId;
    }

    /**
     * 创建新的session
     * @param servletContext
     * @param sessionId
     * @param sessionTimeout 过期时间，单位秒
     * @param redisTemplate redis客户端
     * @param keyPrefix 存入的key前缀
     * @return 返回session
     */
    public static RedisHttpSession createNewSession(ServletContext servletContext, String sessionId, int sessionTimeout, RedisTemplate redisTemplate, String keyPrefix){
        Assert.notNull(redisTemplate, "redisTemplate can not null.");
        Assert.notNull(sessionId, "sessionId can not null.");
        Assert.notNull(keyPrefix, "keyPrefix can not null.");
        RedisHttpSession redisHttpSession= new RedisHttpSession();
        redisHttpSession.setId(sessionId);
        redisHttpSession.setKey(buildKey(keyPrefix, sessionId));
        redisHttpSession.setRedisTemplate(redisTemplate);
        redisHttpSession.setServletContext(servletContext);

        long creationTime = System.currentTimeMillis();
        // 过期时间，分转换成秒
        int maxInactiveInterval = sessionTimeout * SEC60;

        redisHttpSession.setCreationTime(creationTime);
        redisHttpSession.setLastAccessedTime(creationTime);
        redisHttpSession.setMaxInactiveInterval(maxInactiveInterval);

        redisHttpSession.refresh();

        return redisHttpSession;
    }

    /**
     * 创建已经存在的session,数据在redis里面
     * @param sessionId
     * @param servletContext
     * @param redisTemplate redis客户端
     * @param keyPrefix 存入的key前缀
     * @return 返回session
     */
    public static RedisHttpSession createExistSession(String sessionId, ServletContext servletContext, RedisTemplate redisTemplate, String keyPrefix){
        Assert.notNull(redisTemplate, "redisTemplate can not null.");
        Assert.notNull(sessionId, "sessionId can not null.");
        Assert.notNull(keyPrefix, "keyPrefix can not null.");
        RedisHttpSession redisHttpSession= new RedisHttpSession();
        redisHttpSession.setId(sessionId);
        redisHttpSession.setKey(buildKey(keyPrefix, sessionId));
        redisHttpSession.setRedisTemplate(redisTemplate);
        redisHttpSession.setServletContext(servletContext);

        redisHttpSession.refresh();

        return redisHttpSession;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setCreationTime(long creationTime) {
        this.redisTemplate.opsForHash().put(key, CREATION_TIME, String.valueOf(creationTime));
    }

    @Override
    public long getCreationTime() {
        Object createTime = this.redisTemplate.opsForHash().get(key, CREATION_TIME);
        return Long.valueOf(String.valueOf(createTime));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        Object lastAccessedTime = this.redisTemplate.opsForHash().get(key, LAST_ACCESSED_TIME);
        return Long.valueOf(String.valueOf(lastAccessedTime));
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.redisTemplate.opsForHash().put(key, MAX_INACTIVE_INTERVAL, String.valueOf(interval));
    }

    @Override
    public int getMaxInactiveInterval() {
        Object maxInactiveInterval = this.redisTemplate.opsForHash().get(key, MAX_INACTIVE_INTERVAL);
        return Integer.valueOf(String.valueOf(maxInactiveInterval));
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return this.redisTemplate.opsForHash().get(key, SESSION_ATTR + name);
    }

    @Override
    public Object getValue(String name) {
        return getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(getAttributeKeys());
    }

    private Set<String> getAttributeKeys(){
        Set keys = this.redisTemplate.opsForHash().keys(key);
        Set<String> attrNames = new HashSet<>();
        for (Object key : keys){
            String k = String.valueOf(key);
            if (k.startsWith(SESSION_ATTR)){
                attrNames.add(k.substring(SESSION_ATTR.length()));
            }
        }
        return attrNames;
    }


    @Override
    public String[] getValueNames() {
        return getAttributeKeys().toArray(new String[0]);
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.redisTemplate.opsForHash().put(key, SESSION_ATTR + name, value);
    }

    @Override
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        this.redisTemplate.opsForHash().delete(key, name);
    }

    @Override
    public void removeValue(String name) {
        removeAttribute(name);
    }

    @Override
    public void invalidate() {
        this.redisTemplate.delete(key);
    }

    @Override
    public boolean isNew() {
        return false;
    }

    /**
     * update expireTime,accessTime
     */
    public void refresh() {
        // token更新过期时间
        this.redisTemplate.expire(key, getMaxInactiveInterval(), TimeUnit.SECONDS);
        // 设置访问时间
        this.setLastAccessedTime(System.currentTimeMillis());
    }


    public void setLastAccessedTime(long lastAccessedTime) {
        this.redisTemplate.opsForHash().put(key, LAST_ACCESSED_TIME, String.valueOf(lastAccessedTime));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isInvalidated() {
        return !this.redisTemplate.hasKey(key);
    }


    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


}