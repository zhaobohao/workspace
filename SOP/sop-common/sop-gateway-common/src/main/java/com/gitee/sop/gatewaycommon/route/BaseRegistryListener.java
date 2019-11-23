package com.gitee.sop.gatewaycommon.route;

import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;
import com.gitee.sop.gatewaycommon.manager.EnvironmentKeys;
import com.gitee.sop.gatewaycommon.route.RegistryListener;
import com.gitee.sop.gatewaycommon.route.ServiceListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanghc
 */
@Slf4j
public abstract class BaseRegistryListener implements RegistryListener {

    private static final int FIVE_SECONDS = 1000 * 5;

    private Map<String, Long> updateTimeMap = new ConcurrentHashMap<>(16);

    public static List<String> EXCLUDE_SERVICE_ID_LIST = new ArrayList<>(8);

    static {
        EXCLUDE_SERVICE_ID_LIST.add("sop-gateway");
        EXCLUDE_SERVICE_ID_LIST.add("website-server");
    }

    @Autowired
    private ServiceListener serviceListener;

    /**
     * 移除路由信息
     *
     * @param serviceId serviceId
     */
    public void removeRoutes(String serviceId) {
        serviceListener.onRemoveService(serviceId.toLowerCase());
    }

    /**
     * 拉取路由信息
     *
     * @param instance 服务实例
     */
    public void pullRoutes(InstanceDefinition instance) {
        // serviceId统一小写
        instance.setServiceId(instance.getServiceId().toLowerCase());
        serviceListener.onAddInstance(instance);
    }

    protected boolean canOperator(String serviceId) {
        List<String> excludeServiceIdList = getExcludeServiceId();
        for (String excludeServiceId : excludeServiceIdList) {
            if (excludeServiceId.equalsIgnoreCase(serviceId)) {
                return false;
            }
        }
        // nacos会不停的触发事件，这里做了一层拦截
        // 同一个serviceId5秒内允许访问一次
        Long lastUpdateTime = updateTimeMap.getOrDefault(serviceId, 0L);
        long now = System.currentTimeMillis();
        boolean can = now - lastUpdateTime > FIVE_SECONDS;
        if (can) {
            updateTimeMap.put(serviceId, now);
        }
        return can;
    }

    private List<String> getExcludeServiceId() {
        String excludeServiceIds = EnvironmentKeys.SOP_SERVICE_EXCLUDE.getValue();
        List<String> excludeServiceIdList = new ArrayList<>(8);
        if (StringUtils.isNotBlank(excludeServiceIds)) {
            String[] serviceIdArr = excludeServiceIds.split(",");
            excludeServiceIdList.addAll(Arrays.asList(serviceIdArr));
        }
        excludeServiceIdList.addAll(EXCLUDE_SERVICE_ID_LIST);
        return excludeServiceIdList;
    }
}