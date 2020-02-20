package com.gitee.sop.gatewaycommon.route;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;
import com.gitee.sop.gatewaycommon.bean.ServiceRouteInfo;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.manager.BaseRouteCache;
import com.gitee.sop.gatewaycommon.manager.EnvironmentKeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author tanghc
 */
@Slf4j
public class ServiceRouteListener extends BaseServiceListener {

    private static final String SOP_ROUTES_PATH = "/sop/routes";

    private static final String METADATA_SOP_ROUTES_PATH = "sop.routes.path";

    private static final String HEADER_RESTFUL = "restful";

    @Autowired
    private BaseRouteCache<?> baseRouteCache;

    @Autowired
    private RoutesProcessor routesProcessor;

    @Override
    public void onRemoveService(String serviceId) {
        log.info("服务下线，删除路由配置，serviceId: {}", serviceId);
        baseRouteCache.remove(serviceId);
        routesProcessor.removeAllRoutes(serviceId);
    }

    @Override
    public void onAddInstance(InstanceDefinition instance) {
        String serviceName = instance.getServiceId();
        String url = getRouteRequestUrl(instance);
        log.info("拉取路由配置，serviceId: {}, url: {}", serviceName, url);
        ResponseEntity<String> responseEntity = getRestTemplate().postForEntity(url, getHttpEntity(), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            ServiceRouteInfo serviceRouteInfo = JSON.parseObject(body, ServiceRouteInfo.class);
            baseRouteCache.load(serviceRouteInfo, callback -> routesProcessor.saveRoutes(serviceRouteInfo, instance));
        } else {
            log.error("拉取路由配置异常，url: {}, status: {}, body: {}", url, responseEntity.getStatusCodeValue(), responseEntity.getBody());
        }
    }

    protected HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        String restful = EnvironmentKeys.SOP_RESTFUL_ENABLE.getValue();
        boolean enableRestful = "true".equals(restful);
        headers.add(HEADER_RESTFUL, String.valueOf(enableRestful));
        return new HttpEntity<>(headers);
    }

    /**
     * 拉取路由请求url
     *
     * @param instance 服务实例
     * @return 返回最终url
     */
    private String getRouteRequestUrl(InstanceDefinition instance) {
        Map<String, String> metadata = instance.getMetadata();
        String customPath = metadata.get(METADATA_SOP_ROUTES_PATH);
        String homeUrl;
        String servletPath;
        // 如果metadata中指定了获取路由的url
        if (StringUtils.isNotBlank(customPath)) {
            // 自定义完整的url
            if (customPath.startsWith("http")) {
                homeUrl = customPath;
                servletPath = "";
            } else {
                homeUrl = getHomeUrl(instance);
                servletPath = customPath;
            }
        } else {
            // 默认处理
            homeUrl = getHomeUrl(instance);
            String contextPath = this.getContextPath(metadata);
            servletPath = contextPath + SOP_ROUTES_PATH;
        }
        if (StringUtils.isNotBlank(servletPath) && !servletPath.startsWith("/")) {
            servletPath = '/' + servletPath;
        }
        String query = buildQuery();
        return homeUrl + servletPath + query;
    }

    private static String getHomeUrl(InstanceDefinition instance) {
        return "http://" + instance.getIp() + ":" + instance.getPort();
    }
}
