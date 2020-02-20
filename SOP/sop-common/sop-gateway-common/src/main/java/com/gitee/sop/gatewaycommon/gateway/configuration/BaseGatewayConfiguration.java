package com.gitee.sop.gatewaycommon.gateway.configuration;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.gateway.controller.ConfigChannelController;
import com.gitee.sop.gatewaycommon.gateway.controller.ErrorLogController;
import com.gitee.sop.gatewaycommon.gateway.controller.GatewayController;
import com.gitee.sop.gatewaycommon.gateway.controller.GatewayMonitorController;
import com.gitee.sop.gatewaycommon.gateway.filter.GatewayModifyResponseGatewayFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.IndexFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.LimitFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.ParameterFormatterFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.SopLoadBalancerClientFilter;
import com.gitee.sop.gatewaycommon.gateway.handler.GatewayExceptionHandler;
import com.gitee.sop.gatewaycommon.gateway.loadbalancer.SopLoadBalancerClient;
import com.gitee.sop.gatewaycommon.gateway.route.GatewayForwardChooser;
import com.gitee.sop.gatewaycommon.gateway.route.GatewayRouteCache;
import com.gitee.sop.gatewaycommon.gateway.route.GatewayRouteRepository;
import com.gitee.sop.gatewaycommon.manager.AbstractConfiguration;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;


/**
 * @author tanghc
 */
@Slf4j
public class BaseGatewayConfiguration extends AbstractConfiguration {

    public BaseGatewayConfiguration() {
        ApiConfig.getInstance().setUseGateway(true);
    }

    @Bean
    public IndexFilter indexFilter() {
        return new IndexFilter();
    }

    @Bean
    public GatewayController gatewayErrorController() {
        return new GatewayController();
    }

    @Bean
    public ConfigChannelController configChannelController() {
        return new ConfigChannelController();
    }

    @Bean
    public ErrorLogController errorLogController() {
        return new ErrorLogController();
    }

    @Bean
    public GatewayMonitorController gatewayMonitorController() {
        return new GatewayMonitorController();
    }

    /**
     * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
     *
     * @param viewResolversProvider viewResolversProvider
     * @param serverCodecConfigurer serverCodecConfigurer
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler sopErrorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                             ServerCodecConfigurer serverCodecConfigurer) {

        GatewayExceptionHandler jsonExceptionHandler = new GatewayExceptionHandler();
        jsonExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return jsonExceptionHandler;
    }

    /**
     * 处理返回结果
     */
    @Bean
    GatewayModifyResponseGatewayFilter gatewayModifyResponseGatewayFilter() {
        return new GatewayModifyResponseGatewayFilter();
    }

    @Bean
    ParameterFormatterFilter parameterFormatterFilter() {
        return new ParameterFormatterFilter();
    }

    @Bean
    LimitFilter limitFilter() {
        return new LimitFilter();
    }

    @Bean
    GatewayRouteCache gatewayRouteCache(GatewayRouteRepository gatewayRouteRepository) {
        return new GatewayRouteCache(gatewayRouteRepository);
    }

    @Bean
    GatewayRouteRepository gatewayRouteRepository() {
        GatewayRouteRepository gatewayRouteRepository = new GatewayRouteRepository();
        RouteRepositoryContext.setRouteRepository(gatewayRouteRepository);
        return gatewayRouteRepository;
    }

    @Bean
    GatewayForwardChooser gatewayForwardChooser() {
        return new GatewayForwardChooser();
    }

    /**
     * 扩展默认的负载均衡选择，默认使用的是RibbonLoadBalancerClient
     * @param clientFactory
     * @return
     */
    @Bean
    LoadBalancerClient loadBalancerClient(SpringClientFactory clientFactory) {
        return new SopLoadBalancerClient(clientFactory);
    }

    /**
     * 扩展默认的负载均衡过滤器，默认是LoadBalancerClientFilter
     * @param sopLoadBalancerClient SopLoadBalancerClient
     * @param loadBalancerProperties loadBalancerProperties
     * @return
     */
    @Bean
    LoadBalancerClientFilter loadBalancerClientFilter(LoadBalancerClient sopLoadBalancerClient, LoadBalancerProperties loadBalancerProperties) {
        return new SopLoadBalancerClientFilter(sopLoadBalancerClient, loadBalancerProperties);
    }

}
