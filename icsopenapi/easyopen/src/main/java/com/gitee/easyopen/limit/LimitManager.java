package com.gitee.easyopen.limit;

/**
 * @author tanghc
 */
public interface LimitManager {

    /**
     * 从令牌桶中获取令牌，如果使用{@link com.gitee.easyopen.limit.LimitType#TOKEN_BUCKET
     * RateType.TOKEN_BUCKET}限流策略，则该方法生效
     * 
     * @param nameVersion
     *            接口名版本号
     * @return 返回耗时时间，秒
     */
    double acquireToken(String nameVersion);

    /**
     * 是否需要限流，如果使用{@link com.gitee.easyopen.limit.LimitType#LIMIT
     * RateType.LIMIT}限流策略，则该方法生效
     * 
     * @param nameVersion
     *            接口名版本号
     * @return 如果返回true，表示可以执行业务代码，返回false则需要限流
     */
    boolean acquire(String nameVersion);
    
    /**
     * 限流配置管理
     * @return 返回配置管理器
     */
    LimitConfigManager getLimitConfigManager();
    
}
