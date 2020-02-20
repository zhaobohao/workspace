package com.gitee.sop.gatewaycommon.zuul.route;

import com.gitee.sop.gatewaycommon.bean.AbstractTargetRoute;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.route.ForwardInfo;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.core.Ordered;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 路由定位
 *
 * @author tanghc
 */
public class SopRouteLocator implements RouteLocator, Ordered {

    @Autowired
    private ZuulRouteRepository zuulRouteRepository;

    @Autowired
    private ZuulForwardChooser zuulForwardChooser;

    @Override
    public Collection<String> getIgnoredPaths() {
        return Collections.emptyList();
    }

    @Override
    public List<Route> getRoutes() {
        return zuulRouteRepository.getAll()
                .parallelStream()
                .map(AbstractTargetRoute::getTargetRouteDefinition)
                .collect(Collectors.toList());
    }

    /**
     * 这里决定使用哪个路由
     *
     * @param path 当前请求路径
     * @return 返回跳转的路由
     */
    @Override
    public Route getMatchingRoute(String path) {
        ForwardInfo forwardInfo = zuulForwardChooser.getForwardInfo(RequestContext.getCurrentContext());
        String version = forwardInfo.getVersion();
        RequestContext.getCurrentContext().addZuulRequestHeader(ParamNames.HEADER_VERSION_NAME, version);
        return (Route)forwardInfo.getTargetRoute().getTargetRouteDefinition();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
