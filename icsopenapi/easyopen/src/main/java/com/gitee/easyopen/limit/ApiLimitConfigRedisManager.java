package com.gitee.easyopen.limit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

/**
 * 使用redis限流管理
 *
 * @author tanghc
 */
public class ApiLimitConfigRedisManager extends AbstractLimitConfigManager {

    protected static final String REDIS_KEY = "LimitConfigHash";

    private StringRedisTemplate redisTemplate;


    public ApiLimitConfigRedisManager(StringRedisTemplate redisTemplate) {
        super();
        Assert.notNull(redisTemplate, "redisTemplate不能为null");
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void loadLocal() {
    }

    /**
     * 从数据库中读取所有配置。改成关系数据库，重写此方法
     *
     * @return 返回所有配置
     */
    @Override
    protected List<LimitConfig> listFromDb() {
        List<Object> list = this.redisTemplate.opsForHash().values(REDIS_KEY);
        List<LimitConfig> ret = new ArrayList<>(list.size());
        for (Object jsonStr : list) {
            ret.add(JSON.parseObject(String.valueOf(jsonStr), LimitConfig.class));
        }
        return ret;
    }

    /**
     * 保存到数据库。改成关系数据库，重写此方法
     *
     * @param configCached
     * @return 影响行数
     */
    @Override
    protected int doSave(LimitConfig configCached) {
        String hashKey = configCached.getNameVersion();
        this.redisTemplate.opsForHash().put(REDIS_KEY, hashKey, JSON.toJSONString(configCached));
        return 1;
    }


}
