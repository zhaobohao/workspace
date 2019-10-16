package com.gitee.sop.gatewaycommon.manager;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.ServiceRouteInfo;
import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@Slf4j
public abstract class BaseRouteCache<T extends TargetRoute> implements RouteLoader {

    /**
     * KEY:serviceId, value: md5
     */
    private Map<String, String> serviceIdMd5Map = new HashMap<>();

    private RouteRepository<T> routeRepository;

    /**
     * 构建目标路由对象，zuul和gateway定义的路由对象
     *
     * @param serviceRouteInfo       路由服务对象
     * @param gatewayRouteDefinition 路由对象
     * @return 返回目标路由对象
     */
    protected abstract T buildTargetRoute(ServiceRouteInfo serviceRouteInfo, RouteDefinition gatewayRouteDefinition);

    public BaseRouteCache(RouteRepository<T> routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public void load(ServiceRouteInfo serviceRouteInfo, Consumer<Object> callback) {
        try {
            String serviceId = serviceRouteInfo.getServiceId();
            String newMd5 = buildMd5(serviceRouteInfo.getRouteDefinitionList());
            String oldMd5 = serviceIdMd5Map.get(serviceId);
            if (Objects.equals(newMd5, oldMd5)) {
                return;
            }
            serviceIdMd5Map.put(serviceId, newMd5);

            List<RouteDefinition> extRouteDefinitionList = this.getExtRouteDefinitionList(serviceRouteInfo);
            List<RouteDefinition> routeDefinitionList = serviceRouteInfo.getRouteDefinitionList();
            int size = extRouteDefinitionList.size() + routeDefinitionList.size();
            List<RouteDefinition> allRoutes = new ArrayList<>(size);
            if (CollectionUtils.isNotEmpty(extRouteDefinitionList)) {
                allRoutes.addAll(extRouteDefinitionList);
            }
            allRoutes.addAll(routeDefinitionList);
            for (RouteDefinition routeDefinition : allRoutes) {
                T targetRoute = this.buildTargetRoute(serviceRouteInfo, routeDefinition);
                routeRepository.add(targetRoute);
                if (log.isDebugEnabled()) {
                    log.debug("新增路由：{}", JSON.toJSONString(routeDefinition));
                }
            }
            callback.accept(null);
        } catch (Exception e) {
            log.error("加载路由信息失败，serviceRouteInfo:{}", serviceRouteInfo, e);
        }
    }

    /**
     * 构建路由id MD5
     *
     * @param routeDefinitionList 路由列表
     * @return 返回MD5
     */
    private String buildMd5(List<RouteDefinition> routeDefinitionList) {
        List<String> routeIdList = routeDefinitionList.stream()
                .map(JSON::toJSONString)
                .sorted()
                .collect(Collectors.toList());
        String md5Source = org.apache.commons.lang3.StringUtils.join(routeIdList, "");
        return DigestUtils.md5DigestAsHex(md5Source.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 返回附加的路由选项
     * @return 返回附加的路由选项
     */
    protected List<RouteDefinition> getExtRouteDefinitionList(ServiceRouteInfo serviceRouteInfo) {
        return Collections.emptyList();
    }

    @Override
    public void remove(String serviceId) {
        serviceIdMd5Map.remove(serviceId);
        routeRepository.deleteAll(serviceId);
    }
}
