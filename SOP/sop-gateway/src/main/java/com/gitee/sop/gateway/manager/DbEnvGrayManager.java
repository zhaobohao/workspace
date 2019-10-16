package com.gitee.sop.gateway.manager;

import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.gateway.entity.ConfigGray;
import com.gitee.sop.gateway.entity.ConfigGrayInstance;
import com.gitee.sop.gateway.mapper.ConfigGrayInstanceMapper;
import com.gitee.sop.gateway.mapper.ConfigGrayMapper;
import com.gitee.sop.gatewaycommon.bean.ChannelMsg;
import com.gitee.sop.gatewaycommon.bean.ServiceGrayDefinition;
import com.gitee.sop.gatewaycommon.manager.DefaultEnvGrayManager;
import com.gitee.sop.gatewaycommon.zuul.loadbalancer.ServiceGrayConfig;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 存放用户key，这里放在本机内容，如果灰度发布保存的用户id数量偏多，可放在redis中
 *
 * @author tanghc
 */
@Slf4j
@Service
public class DbEnvGrayManager extends DefaultEnvGrayManager {

    private static final int STATUS_ENABLE = 1;

    private static final Function<String[], String> FUNCTION_KEY = arr -> arr[0];
    private static final Function<String[], String> FUNCTION_VALUE = arr -> arr[1];

    @Autowired
    private ConfigGrayMapper configGrayMapper;

    @Autowired
    private ConfigGrayInstanceMapper configGrayInstanceMapper;

    @Override
    public void load() {

        List<ConfigGray> list = configGrayMapper.list(new Query());
        for (ConfigGray configGray : list) {
            this.setServiceGrayConfig(configGray);
        }

        Query query = new Query();
        query.eq("status", STATUS_ENABLE);
        List<ConfigGrayInstance> grayInstanceList = configGrayInstanceMapper.list(query);
        for (ConfigGrayInstance configGrayInstance : grayInstanceList) {
            this.openGray(configGrayInstance.getInstanceId(), configGrayInstance.getServiceId());
        }
    }

    /**
     * 设置用户key
     *
     * @param configGray 灰度配置
     */
    public void setServiceGrayConfig(ConfigGray configGray) {
        if (configGray == null) {
            return;
        }
        String userKeyData = configGray.getUserKeyContent();
        String nameVersionContent = configGray.getNameVersionContent();
        String[] userKeys = StringUtils.split(userKeyData, ',');
        String[] nameVersionList = StringUtils.split(nameVersionContent, ',');
        log.info("灰度配置，userKeys.length:{}, nameVersionList:{}", userKeys.length, Arrays.toString(nameVersionList));

        Set<String> userKeySet = Stream.of(userKeys)
                .collect(Collectors.toCollection(Sets::newConcurrentHashSet));

        Map<String, String> grayNameVersionMap = Stream.of(nameVersionList)
                .map(nameVersion -> StringUtils.split(nameVersion, '='))
                .collect(Collectors.toConcurrentMap(FUNCTION_KEY, FUNCTION_VALUE));

        ServiceGrayConfig serviceGrayConfig = new ServiceGrayConfig();
        serviceGrayConfig.setServiceId(configGray.getServiceId());
        serviceGrayConfig.setUserKeys(userKeySet);
        serviceGrayConfig.setGrayNameVersion(grayNameVersionMap);
        this.saveServiceGrayConfig(serviceGrayConfig);
    }

    @Override
    public void process(ChannelMsg channelMsg) {
        ServiceGrayDefinition userKeyDefinition = channelMsg.toObject(ServiceGrayDefinition.class);
        String serviceId = userKeyDefinition.getServiceId();
        switch (channelMsg.getOperation()) {
            case "set":
                ConfigGray configGray = configGrayMapper.getByColumn("service_id", serviceId);
                setServiceGrayConfig(configGray);
                break;
            case "open":
                openGray(userKeyDefinition.getInstanceId(), serviceId);
                break;
            case "close":
                closeGray(userKeyDefinition.getInstanceId());
                break;
            default:
        }
    }


}
