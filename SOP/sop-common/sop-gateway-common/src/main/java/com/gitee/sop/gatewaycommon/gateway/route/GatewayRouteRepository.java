package com.gitee.sop.gatewaycommon.gateway.route;

import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import com.gitee.sop.gatewaycommon.manager.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
public class GatewayRouteRepository implements RouteDefinitionRepository, RouteRepository<GatewayTargetRoute> {

    private PathMatcher pathMatcher = new AntPathMatcher();

    private final Map<String, GatewayTargetRoute> routes = synchronizedMap(new LinkedHashMap<>());

    @Override
    public Flux<org.springframework.cloud.gateway.route.RouteDefinition> getRouteDefinitions() {
        List<org.springframework.cloud.gateway.route.RouteDefinition> list = routes.values().parallelStream()
                .map(TargetRoute::getTargetRouteDefinition)
                .collect(Collectors.toList());
        return Flux.fromIterable(list);
    }

    @Override
    public Mono<Void> save(Mono<org.springframework.cloud.gateway.route.RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
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
            if (StringUtils.containsAny(pattern, "{") && this.pathMatcher.match(pattern, id)) {
                return entry.getValue();
            }
        }
        return null;
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