package com.gitee.sop.gatewaycommon.zuul.route;

import com.gitee.sop.gatewaycommon.manager.RouteRepository;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 本地存放路由内容的地方
 *
 * @author tanghc
 */
public class ZuulRouteRepository implements RouteRepository<ZuulTargetRoute> {

    private PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * key：nameVersion
     */
    private Map<String, ZuulTargetRoute> nameVersionTargetRouteMap = new ConcurrentHashMap<>(128);

    @Override
    public ZuulTargetRoute get(String id) {
        if (id == null) {
            return null;
        }
        ZuulTargetRoute zuulTargetRoute = nameVersionTargetRouteMap.get(id);
        if (zuulTargetRoute != null) {
            return zuulTargetRoute;
        }
        for (Map.Entry<String, ZuulTargetRoute> entry : nameVersionTargetRouteMap.entrySet()) {
            String pattern = entry.getKey();
            if (this.pathMatcher.match(pattern, id)) {
                return clone(id, entry.getValue());
            }
        }
        return null;
    }

    private ZuulTargetRoute clone(String path, ZuulTargetRoute zuulTargetRoute) {
        Route targetRouteDefinition = zuulTargetRoute.getTargetRouteDefinition();
        Route route = new Route(
                targetRouteDefinition.getId()
                ,path
                ,targetRouteDefinition.getLocation()
                ,targetRouteDefinition.getPrefix()
                ,targetRouteDefinition.getRetryable()
                , null
        );
        return new ZuulTargetRoute(zuulTargetRoute.getServiceRouteInfo()
                , zuulTargetRoute.getRouteDefinition()
                , route);
    }

    @Override
    public Collection<ZuulTargetRoute> getAll() {
        return nameVersionTargetRouteMap.values();
    }

    @Override
    public String add(ZuulTargetRoute targetRoute) {
        nameVersionTargetRouteMap.put(targetRoute.getRouteDefinition().getId(), targetRoute);
        return targetRoute.getRouteDefinition().getId();
    }

    @Override
    public void update(ZuulTargetRoute targetRoute) {
        nameVersionTargetRouteMap.put(targetRoute.getRouteDefinition().getId(), targetRoute);
    }

    @Override
    public void deleteAll(String serviceId) {
        Collection<ZuulTargetRoute> values = nameVersionTargetRouteMap.values();
        List<String> idList = values.stream()
                .filter(zuulTargetRoute -> zuulTargetRoute.getServiceRouteInfo().getServiceId().equalsIgnoreCase(serviceId))
                .map(zuulTargetRoute -> zuulTargetRoute.getRouteDefinition().getId())
                .collect(Collectors.toList());

        for (String id : idList) {
            this.delete(id);
        }
    }

    @Override
    public void delete(String id) {
        nameVersionTargetRouteMap.remove(id);
    }
}
