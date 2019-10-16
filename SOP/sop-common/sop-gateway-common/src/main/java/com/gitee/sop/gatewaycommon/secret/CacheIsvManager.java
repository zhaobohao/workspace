package com.gitee.sop.gatewaycommon.secret;

import com.gitee.sop.gatewaycommon.bean.IsvDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanghc
 */
public class CacheIsvManager implements IsvManager<IsvDefinition> {

    /**
     * key: appKey
     */
    private Map<String, IsvDefinition> isvCache = new ConcurrentHashMap<>(64);

    @Override
    public void load() {

    }

    @Override
    public void update(IsvDefinition isvInfo) {
        isvCache.put(isvInfo.getAppKey(), isvInfo);
    }

    @Override
    public void remove(String appKey) {
        isvCache.remove(appKey);
    }

    @Override
    public IsvDefinition getIsv(String appKey) {
        return isvCache.get(appKey);
    }

    public Map<String, IsvDefinition> getIsvCache() {
        return isvCache;
    }
}
