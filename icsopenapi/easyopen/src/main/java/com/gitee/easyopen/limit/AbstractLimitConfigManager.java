package com.gitee.easyopen.limit;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.bean.ApiDefinition;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.bean.DefinitionHolder;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.easyopen.util.ListUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanghc
 */
public abstract class AbstractLimitConfigManager implements LimitConfigManager {

    private static final String COLUMN_LIMITTYPE = "limitType";
    private static final String COLUMN_LIMITCOUNT = "limitCount";
    private static final String COLUMN_TOKENBUCKETCOUNT = "tokenBucketCount";
    private static final String COLUMN_STATUS = "status";

    /** key:name+version */
    private static Map<String, LimitConfig> limitConfigCache = new ConcurrentHashMap<>(64);

    /**
     * 从数据库中读取所有配置。改成关系数据库，重写此方法
     * 
     * @return 返回所有限流配置
     */
    protected abstract List<LimitConfig> listFromDb();

    /**
     * 保存到数据库。改成关系数据库，重写此方法
     * 
     * @param configCached
     * @return 影响行数
     */
    protected abstract int doSave(LimitConfig configCached);
    
    @Override
    public synchronized void loadToLocalCache() {
        limitConfigCache.clear();

        List<LimitConfig> list = this.listFromDb();
        if (list == null) {
            list = Collections.emptyList();
        }

        for (LimitConfig limitConfig : list) {
            String key = limitConfig.getNameVersion();
            limitConfigCache.put(key, limitConfig);
        }

        Map<String, ApiDefinition> allApi = DefinitionHolder.getApiDefinitionMap();
        Set<Entry<String, ApiDefinition>> allDefinitions = allApi.entrySet();
        ApiConfig apiConfig = ApiContext.getApiConfig();

        for (Entry<String, ApiDefinition> entry : allDefinitions) {
            String key = entry.getKey();
            if (!limitConfigCache.containsKey(key)) {
                LimitConfig limitConfig = this.buildEmptyLimitConfig(entry.getValue(), apiConfig);
                limitConfigCache.put(key, limitConfig);
            }
        }
    }
    
    @Override
    public void loadLimitCache(String json) {
        if(StringUtils.isEmpty(json)) {
            json = "[]";
        }
        List<LimitConfig> list = JSON.parseArray(json, LimitConfig.class);
        this.loadLimitCache(list);
    }
    
    public void loadLimitCache(List<LimitConfig> list) {
        limitConfigCache.clear();
        if (list == null) {
            list = Collections.emptyList();
        }

        for (LimitConfig limitConfig : list) {
            String key = limitConfig.getNameVersion();
            limitConfigCache.put(key, limitConfig);
        }

        Map<String, ApiDefinition> allApi = DefinitionHolder.getApiDefinitionMap();
        Set<Entry<String, ApiDefinition>> allDefinitions = allApi.entrySet();
        ApiConfig apiConfig = ApiContext.getApiConfig();

        for (Entry<String, ApiDefinition> entry : allDefinitions) {
            String key = entry.getKey();
            if (!limitConfigCache.containsKey(key)) {
                LimitConfig limitConfig = this.buildEmptyLimitConfig(entry.getValue(), apiConfig);
                limitConfigCache.put(key, limitConfig);
            }
        }
    }
    
    protected LimitConfig buildEmptyLimitConfig(ApiDefinition apiDefinition, ApiConfig apiConfig) {
        LimitConfig limitConfig = new LimitConfig();
        limitConfig.setName(apiDefinition.getName());
        limitConfig.setVersion(apiDefinition.getVersion());
        limitConfig.setLimitCount(apiConfig.getDefaultLimitCount());
        limitConfig.setTokenBucketCount(apiConfig.getDefaultTokenBucketCount());
        limitConfig.setLimitType(apiConfig.getDefaultLimitType().name());
        limitConfig.setStatus(LimitStatus.CLOSE.getStatus());
        return limitConfig;
    }

    @Override
    public synchronized LimitConfig getApiLimitConfig(String nameVersion) {
        String hashKey = nameVersion;
        return limitConfigCache.get(hashKey);
    }

    @Override
    public int save(LimitConfig limitConfig) {
        String key = limitConfig.getNameVersion();
        LimitConfig configCached = limitConfigCache.get(key);
        CopyUtil.copyPropertiesIgnoreNull(limitConfig, configCached);

        return this.doSave(configCached);
    }

    @Override
    public long getTotal(LimitSearch apiSearch) {
        String name = apiSearch.getName();
        int total = 0;
        Map<String, ApiDefinition> apiDefinitionMap = DefinitionHolder.getApiDefinitionMap();
        if (name == null) {
            return apiDefinitionMap.size();
        }
        Set<String> keys = apiDefinitionMap.keySet();
        for (String key : keys) {
            if (key.contains(name)) {
                total++;
            }
        }
        return total;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LimitConfig> listLimitConfig(final LimitSearch apiSearch) {
        Collection<LimitConfig> allLimitConfig = limitConfigCache.values();

        allLimitConfig = CollectionUtils.select(allLimitConfig, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                LimitConfig limitConfig = (LimitConfig) object;
                boolean retName = true;
                boolean retStatus = true;
                boolean retLimitType = true;
                String name = apiSearch.getName();
                Byte status = apiSearch.getStatus();
                String limitType = apiSearch.getLimitType();

                if (StringUtils.isNotEmpty(name)) {
                    retName = limitConfig.getNameVersion().contains(apiSearch.getName());
                }
                if (status != null) {
                    retStatus = status.byteValue() == limitConfig.getStatus().byteValue();
                }
                if (StringUtils.isNotEmpty(limitType)) {
                    retLimitType = limitType.equals(limitConfig.getLimitType());
                }

                return retName && retStatus && retLimitType;
            }
        });

        List<LimitConfig> retList = new ArrayList<>(allLimitConfig);

        // 排序
        Collections.sort(retList, new Comparator<LimitConfig>() {
            @Override
            public int compare(LimitConfig o1, LimitConfig o2) {
                LimitConfig monitorApiInfo1 = o1;
                LimitConfig monitorApiInfo2 = o2;
                String sortname = apiSearch.getSort();
                String sortorder = apiSearch.getOrder();
                if (Consts.SORT_DESC.equalsIgnoreCase(sortorder)) {
                    monitorApiInfo1 = o2;
                    monitorApiInfo2 = o1;
                }
                if (COLUMN_LIMITTYPE.equals(sortname)) {
                    return monitorApiInfo1.getLimitType().compareTo(monitorApiInfo2.getLimitType());
                } else if (COLUMN_LIMITCOUNT.equals(sortname)) {
                    return Double.compare(monitorApiInfo1.getLimitCount(), monitorApiInfo2.getLimitCount());
                } else if (COLUMN_TOKENBUCKETCOUNT.equals(sortname)) {
                    Integer bucket1Count = monitorApiInfo1.getTokenBucketCount();
                    Integer bucket2Count = monitorApiInfo2.getTokenBucketCount();
                    return Long.compare(bucket1Count == null ? 0 : bucket1Count,
                            bucket2Count == null ? 0 : bucket2Count);
                } else if (COLUMN_STATUS.equals(sortname)) {
                    return monitorApiInfo1.getStatus().compareTo(monitorApiInfo2.getStatus());
                } else {
                    return monitorApiInfo1.getNameVersion().compareTo(monitorApiInfo2.getNameVersion());
                }
            }
        });

        // 分页
        int pageIndex = apiSearch.getPage();
        int pageSize = apiSearch.getRows();

        return ListUtil.page(retList, pageIndex, pageSize);
    }

    public Map<String, LimitConfig> getLimitConfigCache() {
        return limitConfigCache;
    }
}
