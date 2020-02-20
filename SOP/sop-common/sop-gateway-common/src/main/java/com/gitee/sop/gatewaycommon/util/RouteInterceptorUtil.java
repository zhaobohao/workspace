package com.gitee.sop.gatewaycommon.util;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.DefaultRouteInterceptorContext;
import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptor;
import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptorContext;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author tanghc
 */
@Slf4j
public class RouteInterceptorUtil {

    public static void runPreRoute(Object requestContext, ApiParam param, Consumer<RouteInterceptorContext> saveContext) {
        DefaultRouteInterceptorContext defaultRouteInterceptorContext = new DefaultRouteInterceptorContext();
        saveContext.accept(defaultRouteInterceptorContext);
        defaultRouteInterceptorContext.setBeginTimeMillis(System.currentTimeMillis());
        defaultRouteInterceptorContext.setRequestContext(requestContext);
        defaultRouteInterceptorContext.setApiParam(param);
        getRouteInterceptors().forEach(routeInterceptor -> {
            if (routeInterceptor.match(defaultRouteInterceptorContext)) {
                routeInterceptor.preRoute(defaultRouteInterceptorContext);
            }
        });
    }

    public static void runAfterRoute(RouteInterceptorContext routeInterceptorContext) {
        if (routeInterceptorContext == null) {
            return;
        }
        try {
            getRouteInterceptors().forEach(routeInterceptor -> {
                if (routeInterceptor.match(routeInterceptorContext)) {
                    routeInterceptor.afterRoute(routeInterceptorContext);
                }
            });
        } catch (Exception e) {
            log.error("执行路由拦截器异常, apiParam:{}", routeInterceptorContext.getApiParam().toJSONString());
        }
    }

    public static List<RouteInterceptor> getRouteInterceptors() {
        return ApiConfig.getInstance().getRouteInterceptors();
    }

    public static void addInterceptors(Collection<RouteInterceptor> interceptors) {
        List<RouteInterceptor> routeInterceptors = getRouteInterceptors();
        routeInterceptors.addAll(interceptors);
        routeInterceptors.sort(Comparator.comparing(RouteInterceptor::getOrder));
    }
}
