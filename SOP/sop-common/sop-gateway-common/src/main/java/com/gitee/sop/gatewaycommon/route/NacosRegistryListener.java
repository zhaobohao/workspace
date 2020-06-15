package com.gitee.sop.gatewaycommon.route;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 加载服务路由，nacos实现
 *
 * @author tanghc
 */
@Slf4j
public class NacosRegistryListener extends BaseRegistryListener {

    private volatile Set<String> cacheServices = new HashSet<>();

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired(required = false)
    private List<RegistryEvent> registryEventList;

    @Override
    public synchronized void onEvent(ApplicationEvent applicationEvent) {
        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
        ListView<String> servicesOfServer = null;
        try {
            servicesOfServer = namingService.getServicesOfServer(1, Integer.MAX_VALUE);
        } catch (NacosException e) {
            log.error("namingService.getServicesOfServer()错误", e);
        }
        if (servicesOfServer == null || CollectionUtils.isEmpty(servicesOfServer.getData())) {
            return;
        }

        Map<String, Instance> instanceMap = servicesOfServer
                .getData()
                .stream()
                .filter(this::canOperator)
                .map(serviceName -> {
                    try {
                        List<Instance> allInstances = namingService.getAllInstances(serviceName);
                        if (CollectionUtils.isEmpty(allInstances)) {
                            return null;
                        }
                        Instance instance = allInstances.stream()
                                .filter(Instance::isHealthy)
                                .findFirst()
                                .orElse(null);
                        return instance == null ? null : new InstanceInfo(serviceName, instance);
                    } catch (NacosException e) {
                        log.error("namingService.getAllInstances(serviceName)错误，serviceName：{}", serviceName, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(InstanceInfo::getServiceName, InstanceInfo::getInstance));


        Set<String> serviceNames = instanceMap.keySet();

        final Set<String> currentServices = new HashSet<>(serviceNames);
        // 删除现有的，剩下的就是新服务
        currentServices.removeAll(cacheServices);
        // 如果有新的服务注册进来
        if (currentServices.size() > 0) {
            currentServices.forEach(serviceName -> {
                Instance instance = instanceMap.get(serviceName);
                InstanceDefinition instanceDefinition = new InstanceDefinition();
                instanceDefinition.setInstanceId(instance.getInstanceId());
                instanceDefinition.setServiceId(serviceName);
                instanceDefinition.setIp(instance.getIp());
                instanceDefinition.setPort(instance.getPort());
                instanceDefinition.setMetadata(instance.getMetadata());
                pullRoutes(instanceDefinition);
                if (registryEventList != null) {
                    registryEventList.forEach(registryEvent -> registryEvent.onRegistry(instanceDefinition));
                }
            });
        }

        // 如果有服务删除
        Set<String> removedServiceIdList = getRemovedServiceId(serviceNames);
        if (removedServiceIdList.size() > 0) {
            removedServiceIdList.forEach(serviceId->{
                this.removeRoutes(serviceId);
                if (registryEventList != null) {
                    registryEventList.forEach(registryEvent -> registryEvent.onRemove(serviceId));
                }
            });
        }

        cacheServices = new HashSet<>(serviceNames);
    }

    /**
     * 获取已经下线的serviceId
     *
     * @param serviceList 最新的serviceId集合
     * @return 返回已下线的serviceId
     */
    private Set<String> getRemovedServiceId(Set<String> serviceList) {
        Set<String> cache = cacheServices;
        // 删除最新的，剩下就是已经删除的
        cache.removeAll(serviceList);
        return cache;
    }

    @Data
    @AllArgsConstructor
    private static class InstanceInfo {
        private String serviceName;
        private Instance instance;
    }

}
