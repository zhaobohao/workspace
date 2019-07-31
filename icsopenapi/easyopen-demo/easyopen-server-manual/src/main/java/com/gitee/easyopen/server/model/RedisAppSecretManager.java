package com.gitee.easyopen.server.model;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.gitee.easyopen.AppSecretManager;

/**
 * 使用方式:
 * 
 * <pre>
@Autowired
private AppSecretManager appSecretManager;
    
@Override
protected void initApiConfig(ApiConfig apiConfig) {
    ...
    apiConfig.setAppSecretManager(appSecretManager);
    ...
}   

 * </pre>
 * 
 * @author tanghc
 *
 */
//@Component
public class RedisAppSecretManager implements AppSecretManager {

    public static String APP_SECRET_KEY = "easyopen_app_secret";
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    /**
     * 添加一对秘钥
     * @param appKey
     * @param secret
     */
    public void add(String appKey,String secret) {
        stringRedisTemplate.opsForHash().put(APP_SECRET_KEY, appKey, secret);
    }
    
    /**
     * 删除一对秘钥
     * @param appKey
     */
    public void remove(String appKey) {
        stringRedisTemplate.opsForHash().delete(appKey);
    }
    
    
    @Override
    public void addAppSecret(Map<String, String> appSecretStore) {
        stringRedisTemplate.opsForHash().putAll(APP_SECRET_KEY, appSecretStore);
    }

    @Override
    public String getSecret(String appKey) {
        return (String)stringRedisTemplate.opsForHash().get(APP_SECRET_KEY, appKey);
    }

    @Override
    public boolean isValidAppKey(String appKey) {
        if (appKey == null) {
            return false;
        }
        return getSecret(appKey) != null;
    }

}
