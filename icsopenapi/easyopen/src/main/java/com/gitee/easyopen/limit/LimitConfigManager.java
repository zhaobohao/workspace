package com.gitee.easyopen.limit;

import com.gitee.easyopen.ManagerInitializer;

import java.util.List;

/**
 * 限流配置管理
 * 
 * @author tanghc
 */
public interface LimitConfigManager extends ManagerInitializer{
    
    /**
     * 从数据库中读取加载到缓存中.服务器启动完成会先加载此方法.
     */
    void loadToLocalCache();

    /**
     * 加载限流配置到缓存
     * @param json 配置
     */
    void loadLimitCache(String json);

    /**
     * 获取接口的限流配置
     * 
     * @param nameVersion
     *            接口名版本号
     * @return 没有则null
     */
    LimitConfig getApiLimitConfig(String nameVersion);

    /**
     * 总数
     * 
     * @param apiSearch
     * @return 总数
     */
    long getTotal(LimitSearch apiSearch);

    /**
     * 获取限流配置列表
     * 
     * @param apiSearch
     * @return 返回限流配置
     */
    List<LimitConfig> listLimitConfig(LimitSearch apiSearch);

    /**
     * 保存配置到数据库
     * @param limitConfig
     * @return 影响行数
     */
    int save(LimitConfig limitConfig);

}
