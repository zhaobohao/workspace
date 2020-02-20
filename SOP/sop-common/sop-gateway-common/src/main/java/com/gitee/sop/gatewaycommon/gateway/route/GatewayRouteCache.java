package com.gitee.sop.gatewaycommon.gateway.route;

import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.ServiceRouteInfo;
import com.gitee.sop.gatewaycommon.manager.BaseRouteCache;
import com.gitee.sop.gatewaycommon.manager.RouteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
public class GatewayRouteCache extends BaseRouteCache<GatewayTargetRoute> {

    public GatewayRouteCache(RouteRepository<GatewayTargetRoute> routeRepository) {
        super(routeRepository);
    }

    @Override
    protected GatewayTargetRoute buildTargetRoute(ServiceRouteInfo serviceRouteInfo, RouteDefinition routeDefinition) {
        org.springframework.cloud.gateway.route.RouteDefinition targetRoute = new org.springframework.cloud.gateway.route.RouteDefinition();
        targetRoute.setId(routeDefinition.getId());
        String path = routeDefinition.getPath();
        if (path != null && path.contains("{") && path.contains("}")) {
            path = path.replace('{', '?');
            path = path.replace('}', '?');
        }
        targetRoute.setUri(URI.create(routeDefinition.getUri() + "/" + path));
        targetRoute.setOrder(routeDefinition.getOrder());
        // 添加过滤器
        List<FilterDefinition> filterDefinitionList = routeDefinition.getFilters()
                .stream()
                .map(filter -> {
                    FilterDefinition filterDefinition = new FilterDefinition();
                    BeanUtils.copyProperties(filter, filterDefinition);
                    return filterDefinition;
                })
                .collect(Collectors.toList());
        return new GatewayTargetRoute(serviceRouteInfo, routeDefinition, targetRoute);
    }

}
