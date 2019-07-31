package com.gitee.easyopen.limit;

import com.gitee.easyopen.bean.Consts;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * 限流管理
 * @author tanghc
 */
public class ApiLimitManager implements LimitManager {

    private static final String DEFAULT_LIMIT_LUA_FILE_PATH = "/easyopen_script/limit.lua";

    private static final Long REDIS_SUCCESS = 1L;

    private StringRedisTemplate redisTemplate;
    private String limitScript;
    private String limitScriptSha1;

    private LimitConfigManager limitConfigManager;

    public ApiLimitManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        this(redisTemplate, null);
    }

    public ApiLimitManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate,
            LimitConfigManager limitConfigManager) {
        Assert.notNull(redisTemplate, "redisTemplate不能为null");
        this.redisTemplate = new StringRedisTemplate(redisTemplate.getConnectionFactory());
        ClassPathResource limitLua = new ClassPathResource(getLimitLuaFilePath());
        try {
            this.limitScript = IOUtils.toString(limitLua.getInputStream(), Consts.UTF8);
            this.limitScriptSha1 = DigestUtils.sha1Hex(this.limitScript);
        } catch (Exception e) {
            throw new RuntimeException("读取脚本文件失败，脚本路径:" + getLimitLuaFilePath(), e);
        }
        if (limitConfigManager == null) {
            limitConfigManager = new ApiLimitConfigRedisManager(this.redisTemplate);
        }
        this.limitConfigManager = limitConfigManager;
    }

    public String getLimitLuaFilePath() {
        return DEFAULT_LIMIT_LUA_FILE_PATH;
    }

    @Override
    public double acquireToken(String nameVersion) {
        LimitConfig apiRateConfig = this.limitConfigManager.getApiLimitConfig(nameVersion);
        if (LimitType.LIMIT.name().equals(apiRateConfig.getLimitType())) {
            throw new RuntimeException("限制请求数策略无法调用此方法");
        }
        RateLimiter limiter = apiRateConfig.fatchRateLimiter();
        if (limiter == null) {
            throw new RuntimeException("RateLimiter不能为null");
        }
        return limiter.acquire();
    }

    @Override
    public boolean acquire(String nameVersion) {
        LimitConfig apiRateConfig = this.limitConfigManager.getApiLimitConfig(nameVersion);
        if (LimitType.TOKEN_BUCKET.name().equals(apiRateConfig.getLimitType())) {
            throw new RuntimeException("令牌桶策略无法调用此方法");
        }
        String key = "limit:" + apiRateConfig.getNameVersion();
        int limitCount = apiRateConfig.getLimitCount();

        Object result = redisTemplate.execute(new RedisScript<Long>() {
            @Override
            public String getSha1() {
                return limitScriptSha1;
            }

            @Override
            public Class<Long> getResultType() {
                return Long.class;
            }

            @Override
            public String getScriptAsString() {
                return limitScript;
            }

            },
            // KEYS[1] key
            Collections.singletonList(key),
            // ARGV[1] limit
            String.valueOf(limitCount)
        );

        return REDIS_SUCCESS.equals(result);
    }

    @Override
    public LimitConfigManager getLimitConfigManager() {
        return limitConfigManager;
    }

    public void setLimitConfigManager(LimitConfigManager limitConfigManager) {
        this.limitConfigManager = limitConfigManager;
    }

}
