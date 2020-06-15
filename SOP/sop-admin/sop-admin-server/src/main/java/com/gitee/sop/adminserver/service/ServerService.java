package com.gitee.sop.adminserver.service;

import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.adminserver.api.service.param.ServiceSearchParam;
import com.gitee.sop.adminserver.api.service.result.ServiceInstanceVO;
import com.gitee.sop.adminserver.bean.ServiceInfo;
import com.gitee.sop.adminserver.bean.ServiceInstance;
import com.gitee.sop.adminserver.entity.ConfigGrayInstance;
import com.gitee.sop.adminserver.mapper.ConfigGrayInstanceMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@Slf4j
@Service
public class ServerService {

    public static final int GRAY_OPEN = 1;
    @Autowired
    private RegistryService registryService;

    @Autowired
    private ConfigGrayInstanceMapper grayInstanceMapper;

    public List<ServiceInstanceVO> listService(ServiceSearchParam param) {
        List<ServiceInfo> serviceInfos;
        try {
            serviceInfos = registryService.listAllService(1, Integer.MAX_VALUE);
        } catch (Exception e) {
            log.error("获取服务实例失败", e);
            return Collections.emptyList();
        }
        List<ConfigGrayInstance> configGrayInstances = grayInstanceMapper.list(new Query());
        // key: instanceId
        Map<String, ConfigGrayInstance> grayInstanceMap = configGrayInstances.stream()
                .collect(Collectors.toMap(ConfigGrayInstance::getInstanceId, Function.identity()));
        List<ServiceInstanceVO> serviceInfoVoList = new ArrayList<>();
        AtomicInteger idGen = new AtomicInteger(1);
        serviceInfos.stream()
                .filter(serviceInfo -> {
                    if (StringUtils.isBlank(param.getServiceId())) {
                        return true;
                    }
                    return StringUtils.containsIgnoreCase(serviceInfo.getServiceId(), param.getServiceId());
                })
                .forEach(serviceInfo -> {
                    int pid = idGen.getAndIncrement();
                    String serviceId = serviceInfo.getServiceId();
                    ServiceInstanceVO parent = new ServiceInstanceVO();
                    parent.setId(pid);
                    parent.setServiceId(serviceId);
                    parent.setParentId(0);
                    serviceInfoVoList.add(parent);
                    List<ServiceInstance> instanceList = serviceInfo.getInstances();
                    for (ServiceInstance instance : instanceList) {
                        ServiceInstanceVO instanceVO = new ServiceInstanceVO();
                        BeanUtils.copyProperties(instance, instanceVO);
                        int id = idGen.getAndIncrement();
                        instanceVO.setId(id);
                        instanceVO.setParentId(pid);
                        if (instanceVO.getMetadata() == null) {
                            instanceVO.setMetadata(new HashMap<>(8));
                        }
                        bindGrayEnv(instanceVO, grayInstanceMap);
                        serviceInfoVoList.add(instanceVO);
                    }
                });

        return serviceInfoVoList;
    }

    private void bindGrayEnv(ServiceInstanceVO instanceVO, Map<String, ConfigGrayInstance> grayInstanceMap) {
        String instanceId = instanceVO.getInstanceId();
        ConfigGrayInstance configGrayInstance = grayInstanceMap.get(instanceId);
        if (configGrayInstance != null && configGrayInstance.getStatus() == GRAY_OPEN) {
            Map<String, String> metadata = instanceVO.getMetadata();
            metadata.put("env", "gray");
        }
    }
}
