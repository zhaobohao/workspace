package com.gitee.sop.servercommon.manager;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.servercommon.bean.ServiceApiInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author tanghc
 */
@Slf4j
public class RedisApiMetaManager implements ApiMetaManager {
    public static final String API_STORE_KEY = "com.gitee.sop.api";
    public static final String API_CHANGE_CHANNEL = "channel.sop.api.change";

    private StringRedisTemplate redisTemplate;

    public RedisApiMetaManager(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void uploadApi(ServiceApiInfo serviceApiInfo) {
        log.info("上传接口信息到Redis，serviceId：{}, 接口数量：{}", serviceApiInfo.getServiceId(), serviceApiInfo.getApis().size());
        String serviceApiInfoJson = JSON.toJSONString(serviceApiInfo);
        redisTemplate.opsForHash().put(API_STORE_KEY, serviceApiInfo.getServiceId(), serviceApiInfoJson);
        // 发送订阅事件
        redisTemplate.convertAndSend(API_CHANGE_CHANNEL, serviceApiInfoJson);
    }
}
