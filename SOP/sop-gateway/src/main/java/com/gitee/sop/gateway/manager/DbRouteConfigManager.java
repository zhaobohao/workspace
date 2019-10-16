package com.gitee.sop.gateway.manager;

import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.gateway.mapper.ConfigRouteBaseMapper;
import com.gitee.sop.gateway.mapper.ConfigRouteLimitMapper;
import com.gitee.sop.gatewaycommon.bean.ChannelMsg;
import com.gitee.sop.gatewaycommon.bean.RouteConfig;
import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import com.gitee.sop.gatewaycommon.manager.DefaultRouteConfigManager;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    Environment environment;

    @Override
    public void load() {
        loadAllRoute();

        Query query = new Query();
        configRouteBaseMapper.list(query)
                .forEach(configRouteBase -> {
                    String key = configRouteBase.getRouteId();
                    putVal(key, configRouteBase);
                });
    }

    protected void loadAllRoute() {
        Collection<? extends TargetRoute> targetRoutes = RouteRepositoryContext.getRouteRepository().getAll();
        targetRoutes.forEach(targetRoute -> {
                    RouteDefinition routeDefinition = targetRoute.getRouteDefinition();
                    initRouteConfig(routeDefinition);
                });
    }

    protected void initRouteConfig(RouteDefinition routeDefinition) {
        String routeId = routeDefinition.getId();
        RouteConfig routeConfig = newRouteConfig();
        routeConfig.setRouteId(routeId);
        routeConfigMap.put(routeId, routeConfig);
    }

    protected void putVal(String routeId, Object object) {
        this.doUpdate(routeId, object);
    }

    @Override
    public void process(ChannelMsg channelMsg) {
        final RouteConfig routeConfig = channelMsg.toObject( RouteConfig.class);
        switch (channelMsg.getOperation()) {
            case "reload":
                log.info("重新加载路由配置信息，routeConfigDto:{}", routeConfig);
                load();
                break;
            case "update":
                log.info("更新路由配置信息，routeConfigDto:{}", routeConfig);
                update(routeConfig);
                break;
            default:
        }
    }

}
