package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.ConfigLimitDto;
import org.apache.commons.lang.StringUtils;

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

    protected Set<String> buildKeys(ConfigLimitDto configLimitDto) {
        Set<String> keys = new HashSet<>();
        String routeId = Optional.ofNullable(configLimitDto.getRouteId()).orElse("");
        String appKey = Optional.ofNullable(configLimitDto.getAppKey()).orElse("");
        String limitIp = Optional.ofNullable(configLimitDto.getLimitIp()).orElse("").replaceAll("\\s", "");

        String baseKey = routeId.trim() + appKey.trim();
        keys.add(baseKey);

        if (StringUtils.isNotBlank(limitIp)) {
            String[] ips = limitIp.split("\\,|\\，");
            for (String ip : ips) {
                keys.add(ip + baseKey);
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

    @Override
    public void load() {

    }
}
