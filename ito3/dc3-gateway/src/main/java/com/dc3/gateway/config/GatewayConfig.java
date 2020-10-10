package com.dc3.gateway.config;

import com.dc3.gateway.filter.AuthenticGatewayFilterFactory;
import com.dc3.gateway.filter.BlackIpGlobalFilter;
import com.dc3.gateway.hystrix.GatewayHystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Slf4j
@Configuration
public class GatewayConfig {
    private final GatewayHystrix gatewayHystrix;

    public GatewayConfig(GatewayHystrix gatewayHystrix) {
        this.gatewayHystrix = gatewayHystrix;
    }

    @Bean
    public BlackIpGlobalFilter blackIpGlobalFilter() {
        return new BlackIpGlobalFilter();
    }

    @Bean
    public AuthenticGatewayFilterFactory authenticGatewayFilterFactory() {
        return new AuthenticGatewayFilterFactory();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.path("/fallback").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), gatewayHystrix);
    }

}