package com.gitee.sop.adminserver.service;

import com.gitee.sop.adminserver.bean.ChannelMsg;
import com.gitee.sop.adminserver.bean.ConfigLimitDto;
import com.gitee.sop.adminserver.bean.NacosConfigs;
import com.gitee.sop.adminserver.bean.RouteConfigDto;
import com.gitee.sop.adminserver.common.ChannelOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanghc
 */
@Service
@Slf4j
public class RouteConfigService {

    @Autowired
    private ConfigPushService configPushService;

    /**
     * 发送路由配置消息
     * @param routeConfigDto
     * @throws Exception
     */
    public void sendRouteConfigMsg(RouteConfigDto routeConfigDto) {
        ChannelMsg channelMsg = new ChannelMsg(ChannelOperation.ROUTE_CONFIG_UPDATE, routeConfigDto);
        configPushService.publishConfig(NacosConfigs.DATA_ID_ROUTE_CONFIG, NacosConfigs.GROUP_CHANNEL, channelMsg);
    }

    /**
     * 推送路由配置
     * @param routeConfigDto
     * @throws Exception
     */
    public void sendLimitConfigMsg(ConfigLimitDto routeConfigDto) throws Exception {
        ChannelMsg channelMsg = new ChannelMsg(ChannelOperation.LIMIT_CONFIG_UPDATE, routeConfigDto);
        configPushService.publishConfig(NacosConfigs.DATA_ID_LIMIT_CONFIG, NacosConfigs.GROUP_CHANNEL, channelMsg);
    }
}
