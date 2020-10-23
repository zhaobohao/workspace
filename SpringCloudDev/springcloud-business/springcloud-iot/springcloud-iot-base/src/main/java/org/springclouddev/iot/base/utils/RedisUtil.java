package org.springclouddev.iot.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class RedisUtil {
    @Resource
    RedisTemplate<String, Object> j2CacheRedisTemplate;
    /**
     * 判断 Key 是否存在
     *
     * @param key String key
     * @return boolean
     */
    public boolean hasKey(String key) {
        try {
            Boolean hasKey = j2CacheRedisTemplate.hasKey(key);
            return null != hasKey ? hasKey : false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 添加 Key 缓存
     *
     * @param key   String key
     * @param value Object
     */
    public void setKey(String key, Object value) {
        try {
            j2CacheRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 添加 Key 缓存,并设置失效时间
     *
     * @param key   String key
     * @param value Object
     * @param time  Time
     * @param unit  TimeUnit
     */
    public void setKey(String key, Object value, long time, TimeUnit unit) {
        try {
            j2CacheRedisTemplate.opsForValue().set(key, value, time, unit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取 Key 缓存
     *
     * @param key String key
     * @param <T> T
     * @return T
     */
    public <T> T getKey(String key) {
        try {
            Object object = j2CacheRedisTemplate.opsForValue().get(key);
            return (T) object;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除 Keys 缓存
     *
     * @param keys Key Array
     */
    public void removeKey(String... keys) {
        if (null != keys && keys.length > 0) {
            try {
                if (keys.length == 1) {
                    j2CacheRedisTemplate.delete(keys[0]);
                } else {
                    j2CacheRedisTemplate.delete(Arrays.asList(keys));
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 指定键值失效时间
     *
     * @param key  String key
     * @param time Time
     * @param unit TimeUnit
     */
    public void expire(String key, long time, TimeUnit unit) {
        try {
            if (time > 0) {
                j2CacheRedisTemplate.expire(key, time, unit);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取 Key 失效时间
     *
     * @param key  String key
     * @param unit TimeUnit
     * @return long
     */
    public long expire(String key, TimeUnit unit) {
        try {
            Long expire = j2CacheRedisTemplate.getExpire(key, unit);
            return null != expire ? expire : 0L;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0L;
    }
}