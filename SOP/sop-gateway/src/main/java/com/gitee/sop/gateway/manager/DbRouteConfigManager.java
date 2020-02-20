package com.gitee.sop.gateway.manager;

import com.gitee.sop.gateway.mapper.ConfigRouteBaseMapper;
import com.gitee.sop.gateway.mapper.ConfigRouteLimitMapper;
import com.gitee.sop.gateway.mapper.ConfigRouteMapper;
import com.gitee.sop.gatewaycommon.bean.ChannelMsg;
import com.gitee.sop.gatewaycommon.bean.RouteConfig;
import com.gitee.sop.gatewaycommon.manager.DefaultRouteConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanghc
 */
@Slf4j
@Service
public class DbRouteConfigManager extends DefaultRouteConfigManager {

    @Autowired
    ConfigRouteBaseMapper configRouteBaseMapper;

    @Autowired
    ConfigRouteLimitMapper configRouteLimitMapper;

    @Autowired
    ConfigRouteMapper configRouteMapper;

    @Autowired
    Environment environment;

    @Override
    public void load(String serviceId) {
        List<RouteConfig> routeConfigs = StringUtils.isBlank(serviceId) ? configRouteMapper.listAllRouteConfig()
                : configRouteMapper.listRouteConfig(serviceId);
        routeConfigs.forEach(this::save);
    }

    @Override
    public void process(ChannelMsg channelMsg) {
        final RouteConfig routeConfig = channelMsg.toObject(RouteConfig.class);
        switch (channelMsg.getOperation()) {
            case "reload":
                log.info("重新加载路由配置信息，routeConfigDto:{}", routeConfig);
                load(null);
                break;
            case "update":
                log.info("更新路由配置信息，routeConfigDto:{}", routeConfig);
                update(routeConfig);
                break;
            default:
        }
    }

}
