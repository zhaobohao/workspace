package com.gitee.sop.gatewaycommon.zuul.configuration;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.manager.AbstractConfiguration;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import com.gitee.sop.gatewaycommon.param.ParamBuilder;
import com.gitee.sop.gatewaycommon.zuul.ValidateService;
import com.gitee.sop.gatewaycommon.zuul.controller.ConfigChannelController;
import com.gitee.sop.gatewaycommon.zuul.controller.ErrorLogController;
import com.gitee.sop.gatewaycommon.zuul.controller.ZuulIndexController;
import com.gitee.sop.gatewaycommon.zuul.filter.ErrorFilter;
import com.gitee.sop.gatewaycommon.zuul.filter.FormBodyWrapperFilterExt;
import com.gitee.sop.gatewaycommon.zuul.filter.PostResultFilter;
import com.gitee.sop.gatewaycommon.zuul.filter.PreHttpServletRequestWrapperFilter;
import com.gitee.sop.gatewaycommon.zuul.filter.PreLimitFilter;
import com.gitee.sop.gatewaycommon.zuul.filter.PreParameterFormatterFilter;
import com.gitee.sop.gatewaycommon.zuul.filter.PreValidateFilter;
import com.gitee.sop.gatewaycommon.zuul.filter.PreEnvGrayFilter;
import com.gitee.sop.gatewaycommon.zuul.filter.Servlet30WrapperFilterExt;
import com.gitee.sop.gatewaycommon.zuul.route.SopRouteLocator;
import com.gitee.sop.gatewaycommon.zuul.route.ZuulRouteCache;
import com.gitee.sop.gatewaycommon.zuul.route.ZuulRouteRepository;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
import org.springframework.context.annotation.Bean;

/**
 * @author tanghc
 */
public class BaseZuulConfiguration extends AbstractConfiguration {

    @Autowired
    protected ZuulProperties zuulProperties;

    @Autowired
    protected ServerProperties server;

    @Bean
    public ConfigChannelController configChannelController() {
        return new ConfigChannelController();
    }

    @Bean
    public ErrorLogController errorLogController() {
        return new ErrorLogController();
    }

    @Bean
    public ZuulIndexController zuulIndexController() {
        return new ZuulIndexController();
    }


    @Bean
    @ConditionalOnMissingBean
    ParamBuilder<RequestContext> paramBuilder() {
        return ApiConfig.getInstance().getZuulParamBuilder();
    }

    /**
     * 路由仓库
     */
    @Bean
    ZuulRouteRepository zuulRouteRepository() {
        ZuulRouteRepository zuulRouteRepository = new ZuulRouteRepository();
        RouteRepositoryContext.setRouteRepository(zuulRouteRepository);
        return zuulRouteRepository;
    }

    @Bean
    PreHttpServletRequestWrapperFilter preHttpServletRequestWrapperFilter() {
        return new PreHttpServletRequestWrapperFilter();
    }

    @Bean
    FormBodyWrapperFilterExt formBodyWrapperFilterExt() {
        return new FormBodyWrapperFilterExt();
    }

    @Bean
    Servlet30WrapperFilterExt servlet30WrapperFilterExt() {
        return new Servlet30WrapperFilterExt();
    }

    /**
     * 选取路由
     * @param zuulRouteRepository
     * @param proxyRequestHelper
     * @return
     */
    @Bean
    PreDecorationFilter preDecorationFilter(ZuulRouteRepository zuulRouteRepository, ProxyRequestHelper proxyRequestHelper) {
        // 自定义路由
        RouteLocator routeLocator = new SopRouteLocator(zuulRouteRepository);
        return new PreDecorationFilter(routeLocator,
                this.server.getServlet().getContextPath(),
                this.zuulProperties,
                proxyRequestHelper);
    }

    /**
     * 路由管理
     * @param zuulRouteRepository 路由仓库
     */
    @Bean
    ZuulRouteCache zuulRouteCache(ZuulRouteRepository zuulRouteRepository) {
        return new ZuulRouteCache(zuulRouteRepository);
    }

    /**
     * 前置校验
     */
    @Bean
    PreValidateFilter preValidateFilter() {
        return new PreValidateFilter();
    }

    @Bean
    ValidateService validateService() {
        return new ValidateService();
    }

    @Bean
    PreParameterFormatterFilter preParameterFormatterFilter() {
        return new PreParameterFormatterFilter();
    }

    /**
     * 开启限流
     */
    @Bean
    PreLimitFilter preLimitFilter() {
        return new PreLimitFilter();
    }

    /**
     * 决定版本号
     */
    @Bean
    PreEnvGrayFilter preEnvGrayFilter() {
        return new PreEnvGrayFilter();
    }

    /**
     * 错误处理扩展
     */
    @Bean
    ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    /**
     * 结果返回
     */
    @Bean
    PostResultFilter postResultFilter() {
        return new PostResultFilter();
    }


    /**
     * 统一错误处理
     */
    @Bean
    @ConditionalOnMissingBean
    ZuulErrorController baseZuulController() {
        return ApiContext.getApiConfig().getZuulErrorController();
    }

}
