package com.gitee.sop.gatewaycommon.gateway.route;

import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.ServiceRouteInfo;
import com.gitee.sop.gatewaycommon.manager.BaseRouteCache;
import com.gitee.sop.gatewaycommon.manager.RouteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
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
        targetRoute.setUri(URI.create(routeDefinition.getUri() + "#" + path));
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


        LinkedList<PredicateDefinition> predicateDefinitionList = routeDefinition.getPredicates()
                .stream()
                .map(predicate -> {
                    PredicateDefinition predicateDefinition = new PredicateDefinition();
                    BeanUtils.copyProperties(predicate, predicateDefinition);
                    return predicateDefinition;
                })
                .collect(Collectors.toCollection(LinkedList::new));
        // 下面两个自定义的断言添加到顶部，注意：ReadBody需要放在最前面
        // 对应断言：
        // NameVersion ->   com.gitee.sop.gatewaycommon.gateway.route.NameVersionRoutePredicateFactory
        // ReadBody ->      com.gitee.sop.gatewaycommon.gateway.route.ReadBodyRoutePredicateFactory
        predicateDefinitionList.addFirst(new PredicateDefinition("NameVersion=" + routeDefinition.getId()));
        predicateDefinitionList.addFirst(new PredicateDefinition("ReadBody="));

        targetRoute.setFilters(filterDefinitionList);
        targetRoute.setPredicates(predicateDefinitionList);
        return new GatewayTargetRoute(serviceRouteInfo, routeDefinition, targetRoute);
    }

    @Override
    protected List<RouteDefinition> getExtRouteDefinitionList(ServiceRouteInfo serviceRouteInfo) {
        // 在第一个位置放一个没用的路由，SpringCloudGateway会从第二个路由开始找，原因不详
        RouteDefinition routeDefinition = new RouteDefinition();
        String name = "_first_route_";
        String version = String.valueOf(System.currentTimeMillis());
        routeDefinition.setId(name + version);
        routeDefinition.setName(name);
        routeDefinition.setVersion(version);
        routeDefinition.setUri("lb://" + serviceRouteInfo.getServiceId());
        routeDefinition.setOrder(Integer.MIN_VALUE);

        return Collections.singletonList(routeDefinition);
    }
}
