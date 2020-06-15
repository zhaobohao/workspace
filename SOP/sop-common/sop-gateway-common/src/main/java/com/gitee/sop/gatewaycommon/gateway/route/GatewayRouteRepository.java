package com.gitee.sop.gatewaycommon.gateway.route;

import com.gitee.sop.gatewaycommon.bean.AbstractTargetRoute;
import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.manager.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.synchronizedMap;

/**
 * 路由存储管理，负责动态更新路由
 *
 * @author tanghc
 */
@Slf4j
public class GatewayRouteRepository implements RouteRepository<GatewayTargetRoute>, RouteLocator {

    private PathMatcher pathMatcher = new AntPathMatcher();

    private final Map<String, GatewayTargetRoute> routes = synchronizedMap(new LinkedHashMap<>());

    @Autowired
    private RouteLocatorBuilder routeLocatorBuilder;

    @Autowired
    private ApplicationContext applicationContext;

    private volatile RouteLocator routeLocator;

    @Override
    public Flux<Route> getRoutes() {
        if (routeLocator == null) {
            return Flux.empty();
        }
        return routeLocator.getRoutes();
    }

    public void refresh() {
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();
        List<RouteDefinition> routeDefinitionList = this.routes.values()
                .stream()
                .map(AbstractTargetRoute::getRouteDefinition)
                .collect(Collectors.toList());
        routeDefinitionList.forEach(routeDefinition -> builder.route(routeDefinition.getId(),
                r -> r.path(routeDefinition.getPath())
                        .uri(routeDefinition.getUri())));
        this.routeLocator = builder.build();

        // 触发
        applicationContext.publishEvent(new RefreshRoutesEvent(new Object()));
    }

    /**
     * 根据ID获取路由
     */
    @Override
    public GatewayTargetRoute get(String id) {
        if (id == null) {
            return null;
        }
        GatewayTargetRoute gatewayTargetRoute = routes.get(id);
        if (gatewayTargetRoute != null) {
            return gatewayTargetRoute;
        }
        for (Map.Entry<String, GatewayTargetRoute> entry : routes.entrySet()) {
            // /food/get/?id?
            String pattern = entry.getKey();
            if (this.pathMatcher.match(pattern, id)) {
                return clone(id, entry.getValue());
            }
        }
        return null;
    }

    private GatewayTargetRoute clone(String path, GatewayTargetRoute gatewayTargetRoute) {
        String prefix = "/" + gatewayTargetRoute.getServiceRouteInfo().getServiceId();
        if (path.startsWith(prefix)) {
            path = path.substring(prefix.length());
        }
        RouteDefinition routeDefinition = gatewayTargetRoute.getRouteDefinition();
        RouteDefinition newRouteDefinition = new RouteDefinition();
        BeanUtils.copyProperties(routeDefinition, newRouteDefinition);
        newRouteDefinition.setPath(path);
        return new GatewayTargetRoute(gatewayTargetRoute.getServiceRouteInfo()
                , newRouteDefinition
                , gatewayTargetRoute.getTargetRouteDefinition());
    }


    @Override
    public Collection<GatewayTargetRoute> getAll() {
        return routes.values();
    }

    /**
     * 增加路由
     */
    @Override
    public String add(GatewayTargetRoute targetRoute) {
        RouteDefinition routeDefinition = targetRoute.getRouteDefinition();
        routes.put(routeDefinition.getId(), targetRoute);
        return "success";
    }

    @Override
    public void update(GatewayTargetRoute targetRoute) {
        RouteDefinition baseRouteDefinition = targetRoute.getRouteDefinition();
        routes.put(baseRouteDefinition.getId(), targetRoute);
    }

    /**
     * 删除路由
     */
    @Override
    public void delete(String id) {
        routes.remove(id);
    }

    @Override
    public void deleteAll(String serviceId) {
        List<String> idList = this.routes.values().stream()
                .filter(zuulTargetRoute -> StringUtils.equalsIgnoreCase(serviceId, zuulTargetRoute.getServiceRouteInfo().getServiceId()))
                .map(zuulTargetRoute -> zuulTargetRoute.getRouteDefinition().getId())
                .collect(Collectors.toList());

        for (String id : idList) {
            this.delete(id);
        }
    }

}