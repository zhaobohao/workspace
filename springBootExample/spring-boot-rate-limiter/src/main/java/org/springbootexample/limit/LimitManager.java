package org.springbootexample.limit;

/**
 * 限流管理
 * @author zhaobo
 */
public interface LimitManager {

    /**
     * 从令牌桶中获取令牌，如果使用{@link LimitType#TOKEN_BUCKET
     * RateType.TOKEN_BUCKET}限流策略，则该方法生效
     * 
     * @param routeConfig 路由配置
     * @return 返回耗时时间，秒
     */
    double acquireToken(LimitParam routeConfig);

    /**
     * 是否需要限流，如果使用{@link LimitType#LEAKY_BUCKET
     * RateType.LIMIT}限流策略，则该方法生效
     * 
     * @param routeConfig 路由配置
     * @return 如果返回true，表示可以执行业务代码，返回false则需要限流
     */
    boolean acquire(String key,LimitParam routeConfig);

}
