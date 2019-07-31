package com.gitee.easyopen.limit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 本地限流设置，如果要支持分布式业务限流，使用ApiLimitManager
 * @author tanghc
 */
public class ApiLimitLocalManager implements LimitManager {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private LoadingCache<Long, AtomicLong> counter =
            CacheBuilder.newBuilder()
                    .expireAfterWrite(2, TimeUnit.SECONDS)
                    .build(new CacheLoader<Long, AtomicLong>() {
                        @Override
                        public AtomicLong load(Long seconds) throws Exception {
                            return new AtomicLong(0);
                        }
                    });

    private LimitConfigManager limitConfigManager = new ApiLimitConfigLocalManager();

    public ApiLimitLocalManager() {
    }

    public ApiLimitLocalManager(LoadingCache<Long, AtomicLong> counter, LimitConfigManager limitConfigManager) {
        this(limitConfigManager);
        Assert.notNull(counter, "counter不能为null");
        this.counter = counter;
    }

    public ApiLimitLocalManager(LimitConfigManager limitConfigManager) {
        Assert.notNull(limitConfigManager, "limitConfigManager不能为null");
        this.limitConfigManager = limitConfigManager;
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
        int limit = apiRateConfig.getLimitCount();
        long currentSeconds = System.currentTimeMillis() / 1000;

        try {
            // 被限流了
            if(counter.get(currentSeconds).incrementAndGet() > limit) {
                return false;
            } else {
                return true;
            }
        } catch (ExecutionException e) {
            logger.error("限流出错", e);
            return false;
        }
    }

    @Override
    public LimitConfigManager getLimitConfigManager() {
        return limitConfigManager;
    }

    public void setLimitConfigManager(LimitConfigManager limitConfigManager) {
        this.limitConfigManager = limitConfigManager;
    }

    public LoadingCache<Long, AtomicLong> getCounter() {
        return counter;
    }

    public void setCounter(LoadingCache<Long, AtomicLong> counter) {
        this.counter = counter;
    }
}
