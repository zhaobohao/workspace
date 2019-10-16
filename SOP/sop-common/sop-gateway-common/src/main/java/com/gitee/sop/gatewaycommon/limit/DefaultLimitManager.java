package com.gitee.sop.gatewaycommon.limit;

import com.gitee.sop.gatewaycommon.bean.ConfigLimitDto;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tanghc
 */
@Slf4j
public class DefaultLimitManager implements LimitManager {

    @Override
    public double acquireToken(ConfigLimitDto routeConfig) {
        if (routeConfig.getLimitStatus() == ConfigLimitDto.LIMIT_STATUS_CLOSE) {
            return 0;
        }
        if (LimitType.LEAKY_BUCKET.getType() == routeConfig.getLimitType().byteValue()) {
            return 0;
        }
        return routeConfig.fetchRateLimiter().acquire();
    }


    @Override
    public boolean acquire(ConfigLimitDto routeConfig) {
        if (routeConfig.getLimitStatus() == ConfigLimitDto.LIMIT_STATUS_CLOSE) {
            return true;
        }
        if (LimitType.TOKEN_BUCKET.getType() == routeConfig.getLimitType().byteValue()) {
            return true;
        }
        int execCountPerSecond = routeConfig.getExecCountPerSecond();
        long currentSeconds = System.currentTimeMillis() / 1000;
        try {
            LoadingCache<Long, AtomicLong> counter = routeConfig.getCounter();
            // 被限流了
            if (counter.get(currentSeconds).incrementAndGet() > execCountPerSecond) {
                return false;
            } else {
                return true;
            }
        } catch (ExecutionException e) {
            log.error("漏桶限流出错，routeConfig", routeConfig, e);
            return false;
        }
    }

}
