package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.ConfigLimitDto;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanghc
 */
public class DefaultLimitConfigManager implements LimitConfigManager {

    public static final int LIMIT_STATUS_CLOSED = 0;
    /**
     * key: limitKey
     */
    protected static Map<String, ConfigLimitDto> limitCache = new ConcurrentHashMap<>();

    protected static Map<Long, Set<String>> idKeyMap = new HashMap<>();

    @Override
    public void load(String serviceId) {

    }

    @Override
    public void update(ConfigLimitDto configLimitDto) {
        Long id = configLimitDto.getId();
        this.remove(id);
        if (configLimitDto.getLimitStatus().intValue() == LIMIT_STATUS_CLOSED) {
            return;
        }
        configLimitDto.initRateLimiter();
        Set<String> keys = this.buildKeys(configLimitDto);
        idKeyMap.put(id, keys);
        for (String key : keys) {
            this.doUpdate(key, configLimitDto);
        }
    }

    /**
     *                  // 根据路由ID限流
     *                 routeId,
     *                 // 根据appKey限流
     *                 appKey,
     *                 // 根据路由ID + appKey限流
     *                 routeId + appKey,
     *
     *                 // 根据ip限流
     *                 ip,
     *                 // 根据ip+路由id限流
     *                 ip + routeId,
     *                 // 根据ip+appKey限流
     *                 ip + appKey,
     *                 // 根据ip+路由id+appKey限流
     *                 ip + routeId + appKey,
     * @param configLimitDto
     * @return
     */
    protected Set<String> buildKeys(ConfigLimitDto configLimitDto) {
        Set<String> keys = new HashSet<>();
        String routeId = Optional.ofNullable(configLimitDto.getRouteId()).orElse("");
        String appKey = Optional.ofNullable(configLimitDto.getAppKey()).orElse("");
        String limitIp = Optional.ofNullable(configLimitDto.getLimitIp()).orElse("").replaceAll("\\s", "");

        // 根据路由ID限流
        if (StringUtils.isNotBlank(routeId) && StringUtils.isBlank(appKey) && StringUtils.isBlank(limitIp)) {
            keys.add(routeId);
        }
        // 根据appKey限流
        if (StringUtils.isBlank(routeId) && StringUtils.isNotBlank(appKey) && StringUtils.isBlank(limitIp)) {
            keys.add(appKey);
        }
        // 根据路由ID + appKey限流
        if (StringUtils.isNotBlank(routeId) && StringUtils.isNotBlank(appKey) && StringUtils.isBlank(limitIp)) {
            keys.add(routeId.trim() + appKey.trim());
        }
        Set<String> baseKeys = new HashSet<>(keys);
        // 根据ip限流
        if (StringUtils.isBlank(routeId) && StringUtils.isBlank(appKey) && StringUtils.isNotBlank(limitIp)) {
            String[] ips = limitIp.split("\\,|\\，");
            keys.addAll(Arrays.asList(ips));
        }
        // 根据ip+路由id限流
        // 根据ip+appKey限流
        // 根据ip+路由id+appKey限流
        if (StringUtils.isNotBlank(limitIp)) {
            String[] ips = limitIp.split("\\,|\\，");
            for (String ip : ips) {
                for (String baseKey : baseKeys) {
                    keys.add(ip + baseKey);
                }
            }
        }
        return keys;
    }

    protected void doUpdate(String key, ConfigLimitDto configLimitDto) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        limitCache.put(key, configLimitDto);
    }

    protected void remove(Long id) {
        Set<String> list = idKeyMap.getOrDefault(id, Collections.emptySet());
        for (String key : list) {
            limitCache.remove(key);
        }
    }

    @Override
    public ConfigLimitDto get(String limitKey) {
        if (StringUtils.isBlank(limitKey)) {
            return null;
        }
        return limitCache.get(limitKey);
    }

}
