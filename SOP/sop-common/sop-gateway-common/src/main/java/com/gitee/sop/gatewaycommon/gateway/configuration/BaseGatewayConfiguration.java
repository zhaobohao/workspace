package com.gitee.sop.gatewaycommon.gateway.configuration;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.gateway.filter.EnvGrayFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.GatewayModifyResponseGatewayFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.LimitFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.LoadBalancerClientExtFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.ParameterFormatterFilter;
import com.gitee.sop.gatewaycommon.gateway.filter.ValidateFilter;
import com.gitee.sop.gatewaycommon.gateway.handler.GatewayExceptionHandler;
import com.gitee.sop.gatewaycommon.gateway.route.GatewayRouteCache;
import com.gitee.sop.gatewaycommon.gateway.route.GatewayRouteRepository;
import com.gitee.sop.gatewaycommon.gateway.route.NameVersionRoutePredicateFactory;
import com.gitee.sop.gatewaycommon.gateway.route.ReadBodyRoutePredicateFactory;
import com.gitee.sop.gatewaycommon.manager.AbstractConfiguration;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import com.gitee.sop.gatewaycommon.param.ParamBuilder;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.Collections;
import java.util.List;


/**
 * @author tanghc
 */
public class BaseGatewayConfiguration extends AbstractConfiguration {

    public BaseGatewayConfiguration() {
        ApiConfig.getInstance().setUseGateway(true);
    }

    @Value("${sop.restful.path:/rest}")
    private String restPath;

    /**
     * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
     *
     * @param viewResolversProvider viewResolversProvider
     * @param serverCodecConfigurer serverCodecConfigurer
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                             ServerCodecConfigurer serverCodecConfigurer) {

        GatewayExceptionHandler jsonExceptionHandler = new GatewayExceptionHandler();
        jsonExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return jsonExceptionHandler;
    }

    @Bean
    @ConditionalOnMissingBean
    ParamBuilder<ServerWebExchange> paramBuilder() {
        return ApiConfig.getInstance().getGatewayParamBuilder();
    }

    /**
     * 处理返回结果
     */
    @Bean
    GatewayModifyResponseGatewayFilter gatewayModifyResponseGatewayFilter() {
        return new GatewayModifyResponseGatewayFilter();
    }

    /**
     * 读取post请求参数
     */
    @Bean
    ReadBodyRoutePredicateFactory readBodyRoutePredicateFactory() {
        return new ReadBodyRoutePredicateFactory();
    }

    @Bean
    NameVersionRoutePredicateFactory paramRoutePredicateFactory() {
        return new NameVersionRoutePredicateFactory();
    }

    @Bean
    ValidateFilter validateFilter() {
        return new ValidateFilter();
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
    LoadBalancerClientExtFilter loadBalancerClientExtFilter() {
        return new LoadBalancerClientExtFilter();
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
    EnvGrayFilter envGrayFilter() {
        return new EnvGrayFilter();
    }

    /**
     * 307 Temporary Redirect（临时重定向）:
     * <p>
     * 在这种情况下，请求应该与另一个URI重复，但后续的请求应仍使用原始的URI。
     * 与302相反，当重新发出原始请求时，不允许更改请求方法。 例如，应该使用另一个POST请求来重复POST请求
     * <p>
     * 308 Permanent Redirect （永久重定向）:
     * <p>
     * 请求和所有将来的请求应该使用另一个URI重复。
     * 307和308重复302和301的行为，但不允许HTTP方法更改。 例如，将表单提交给永久重定向的资源可能会顺利进行。
     * <p>
     * https://www.cnblogs.com/wuguanglin/p/redirect.html
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "sop.restful.enable", havingValue = "true")
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.GET(restPath + "/**"), (serverRequest) -> {
            String url = serverRequest.path();
            int index = url.indexOf(restPath);
            // 取/rest的后面部分
            String path = url.substring(index + restPath.length());
            String query = ParamNames.API_NAME + "=" + path + "&" + ParamNames.VERSION_NAME + "=";
            return ServerResponse
                    .temporaryRedirect(URI.create("/?" + query))
                    .build();
        });
    }
}
