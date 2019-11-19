package com.gitee.sop.servercommon.manager;

import com.gitee.sop.servercommon.bean.ServiceApiInfo;
import com.gitee.sop.servercommon.route.RouteDefinition;
import com.gitee.sop.servercommon.route.ServiceRouteInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tanghc
 */
@Slf4j
public class ServiceRouteInfoBuilder {

    /**
     * 网关对应的LoadBalance协议
     */
    private static final String PROTOCOL_LOAD_BALANCE = "lb://";

    private static final String PATH_SPLIT = "/";

    private static final String DEFAULT_CONTEXT_PATH = "/";

    private Environment environment;

    public ServiceRouteInfoBuilder(Environment environment) {
        this.environment = environment;
    }

    public ServiceRouteInfo build(ServiceApiInfo serviceApiInfo) {
        return this.buildServiceGatewayInfo(serviceApiInfo);
    }

    /**
     * 构建接口信息，符合spring cloud gateway的格式
     *
     * @param serviceApiInfo 服务接口信息
     * @return 返回服务路由信息
     */
    protected ServiceRouteInfo buildServiceGatewayInfo(ServiceApiInfo serviceApiInfo) {
        List<ServiceApiInfo.ApiMeta> apis = serviceApiInfo.getApis();
        List<RouteDefinition> routeDefinitionList = new ArrayList<>(apis.size());
        for (ServiceApiInfo.ApiMeta apiMeta : apis) {
            RouteDefinition routeDefinition = this.buildGatewayRouteDefinition(serviceApiInfo, apiMeta);
            routeDefinitionList.add(routeDefinition);
        }
        ServiceRouteInfo serviceRouteInfo = new ServiceRouteInfo();
        serviceRouteInfo.setServiceId(serviceApiInfo.getServiceId());
        serviceRouteInfo.setRouteDefinitionList(routeDefinitionList);
        return serviceRouteInfo;
    }


    protected RouteDefinition buildGatewayRouteDefinition(ServiceApiInfo serviceApiInfo, ServiceApiInfo.ApiMeta apiMeta) {
        RouteDefinition routeDefinition = new RouteDefinition();
        // 唯一id规则：接口名 + 版本号
        String routeId = apiMeta.fetchNameVersion();
        if (!apiMeta.isOriginalMapping()) {
            this.checkPath(routeId, "接口定义（" + routeId + "）不能有斜杠字符'/'");
        }
        BeanUtils.copyProperties(apiMeta, routeDefinition);
        routeDefinition.setId(routeId);
        routeDefinition.setFilters(Collections.emptyList());
        routeDefinition.setPredicates(Collections.emptyList());
        String uri = this.buildUri(serviceApiInfo, apiMeta);
        String path = this.buildServletPath(serviceApiInfo, apiMeta);
        routeDefinition.setUri(uri);
        routeDefinition.setPath(path);
        return routeDefinition;
    }

    protected String buildUri(ServiceApiInfo serviceApiInfo, ServiceApiInfo.ApiMeta apiMeta) {
        return PROTOCOL_LOAD_BALANCE + serviceApiInfo.getServiceId();
    }

    protected String buildServletPath(ServiceApiInfo serviceApiInfo, ServiceApiInfo.ApiMeta apiMeta) {
        String contextPath = getContextPathCompatibility();
        String servletPath = apiMeta.getPath();
        if (servletPath == null) {
            servletPath = "";
        }
        if (!servletPath.startsWith(PATH_SPLIT)) {
            servletPath = PATH_SPLIT + servletPath;
        }
        if (DEFAULT_CONTEXT_PATH.equals(contextPath)) {
            return servletPath;
        } else {
            return contextPath + servletPath;
        }
    }

    /**
     * 获取context-path，兼容老版本
     * @return 返回context-path，没有返回"/"
     */
    private String getContextPathCompatibility() {
        return environment.getProperty("server.servlet.context-path",
                environment.getProperty("server.context-path", DEFAULT_CONTEXT_PATH)
        );
    }

    private void checkPath(String path, String errorMsg) {
        if (path.contains(PATH_SPLIT)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public Environment getEnvironment() {
        return environment;
    }
}
