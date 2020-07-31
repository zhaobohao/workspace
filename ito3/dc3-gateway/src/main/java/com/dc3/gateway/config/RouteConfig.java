

package com.dc3.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 自定义Route配置
 *
 * @author pnoker
 */
@Slf4j
@Order(10)
@Configuration
public class RouteConfig {

    /**
     * 根据 HostAddress 进行限流
     *
     * @return KeyResolver
     */
    @Bean
    public KeyResolver hostKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString());
    }

    /**
     * Redis 令牌桶 限流
     *
     * @return RedisRateLimiter
     */
    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(100, 2000);
    }

    /**
     * 自定义 RouteLocator
     *
     * @param builder RouteLocatorBuilder
     * @return RouteLocator
     */
    @Bean
    public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("token_salt",
                        r -> r.path("/api/v3/token/salt")
                                .filters(
                                        f -> f.setPath("/auth/token/salt")
                                                .requestRateLimiter(l -> l.setKeyResolver(hostKeyResolver()).setRateLimiter(redisRateLimiter()))
                                                .hystrix(h -> h.setName("default").setFallbackUri("forward:/fallback"))
                                ).uri("lb://dc3-auth")
                )
                .route("generate_token",
                        r -> r.path("/api/v3/token/generate")
                                .filters(
                                        f -> f.setPath("/auth/token/generate")
                                                .requestRateLimiter(l -> l.setKeyResolver(hostKeyResolver()).setRateLimiter(redisRateLimiter()))
                                                .hystrix(h -> h.setName("default").setFallbackUri("forward:/fallback"))
                                ).uri("lb://dc3-auth")
                )
                .route("cancel_token",
                        r -> r.path("/api/v3/token/cancel")
                                .filters(
                                        f -> f.setPath("/auth/token/cancel")
                                                .requestRateLimiter(l -> l.setKeyResolver(hostKeyResolver()).setRateLimiter(redisRateLimiter()))
                                                .hystrix(h -> h.setName("default").setFallbackUri("forward:/fallback"))
                                ).uri("lb://dc3-auth")
                )
                .route("register_user",
                        r -> r.path("/api/v3/register")
                                .filters(
                                        f -> f.setPath("/auth/user/add")
                                                .requestRateLimiter(l -> l.setKeyResolver(hostKeyResolver()).setRateLimiter(redisRateLimiter()))
                                                .hystrix(h -> h.setName("default").setFallbackUri("forward:/fallback"))
                                ).uri("lb://dc3-auth")
                )
                .build();
    }


}
