package com.gitee.easyopen.server.interceptor;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.auth0.jwt.interfaces.Claim;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiMeta;
import com.gitee.easyopen.support.BaseLockInterceptor;

/**
 * 使用分布式锁防止表单重复提交
 * 
 * @author tanghc
 */
public class LockInterceptor extends BaseLockInterceptor {

    private StringRedisTemplate redisTemplate;

    public LockInterceptor() {
        redisTemplate = ApiContext.getApplicationContext().getBean(StringRedisTemplate.class);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    @Override
    protected String getUserId() {
        Map<String, Claim> jwtData = ApiContext.getJwtData();
        String id = jwtData.get("id").asString(); // 用户id
        return id;
    }

    @Override
    public boolean match(ApiMeta apiMeta) {
        return "userlock.test".equals(apiMeta.getName()); // 只针对这个接口
    }
}
