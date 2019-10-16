package com.gitee.sop.gatewaycommon.route;

import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;
import com.gitee.sop.gatewaycommon.loadbalancer.EurekaEnvironmentServerChooser;
import com.gitee.sop.gatewaycommon.manager.EnvironmentKeys;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.context.ApplicationEvent;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 加载服务路由，eureka实现
 *
 * @author tanghc
 */
public class EurekaRegistryListener extends BaseRegistryListener {

    static {
        System.setProperty(EnvironmentKeys.ZUUL_CUSTOM_RULE_CLASSNAME.getKey(), EurekaEnvironmentServerChooser.class.getName());
    }

    private Set<String> cacheServices = new HashSet<>();

    /**
     * 注册中心触发事件，可以从中获取服务<br>
     *
     * 这个方法做的事情有2个：<br>
     *
     * 1. 找出新注册的服务，调用pullRoutes方法<br>
     * 2. 找出删除的服务，调用removeRoutes方法<br>
     *
     * @param applicationEvent 事件体
     */
    @Override
    public void onEvent(ApplicationEvent applicationEvent) {
        Object source = applicationEvent.getSource();
        CloudEurekaClient cloudEurekaClient = (CloudEurekaClient) source;
        Applications applications = cloudEurekaClient.getApplications();
        List<Application> registeredApplications = applications.getRegisteredApplications();
        List<String> serviceList = registeredApplications
                .stream()
                .map(Application::getName)
                .collect(Collectors.toList());

        final Set<String> currentServices = new HashSet<>(serviceList);
        currentServices.removeAll(cacheServices);
        // 如果有新的服务注册进来
        if (currentServices.size() > 0) {
            List<Application> newApplications = registeredApplications.stream()
                    .filter(application -> this.canOperator(application.getName())
                            && currentServices.contains(application.getName()))
                    .collect(Collectors.toList());

            this.doRegister(newApplications);
        }

        cacheServices.removeAll(new HashSet<>(serviceList));
        // 如果有服务删除
        if (cacheServices.size() > 0) {
            this.doRemove(cacheServices);
        }
        // 缓存最新服务
        cacheServices = new HashSet<>(serviceList);
    }

    private void doRegister(List<Application> registeredApplications) {
        registeredApplications.forEach(application -> {
            List<InstanceInfo> instances = application.getInstances();
            if (CollectionUtils.isNotEmpty(instances)) {
                instances.sort(Comparator.comparing(InstanceInfo::getLastUpdatedTimestamp).reversed());
                InstanceInfo instanceInfo = instances.get(0);
                InstanceDefinition instanceDefinition = new InstanceDefinition();
                instanceDefinition.setInstanceId(instanceInfo.getInstanceId());
                instanceDefinition.setServiceId(instanceInfo.getAppName());
                instanceDefinition.setIp(instanceInfo.getIPAddr());
                instanceDefinition.setPort(instanceInfo.getPort());
                instanceDefinition.setMetadata(instanceInfo.getMetadata());
                pullRoutes(instanceDefinition);
            }
        });
    }

    private void doRemove(Set<String> deletedServices) {
        deletedServices.forEach(this::removeRoutes);
    }

}
