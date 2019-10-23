package org.springclouddev.gateway.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springclouddev.gateway.props.AuthProperties;
import org.springclouddev.gateway.provider.AuthProvider;
import org.springclouddev.gateway.provider.ResponseProvider;
import org.springclouddev.gateway.utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 鉴权认证
 *
 * @author Chill
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
    private AuthProperties authProperties;
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isSkip(path)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse resp = exchange.getResponse();
        String headerToken = exchange.getRequest().getHeaders().getFirst(AuthProvider.AUTH_KEY);
        String paramToken = exchange.getRequest().getQueryParams().getFirst(AuthProvider.AUTH_KEY);
        if (StringUtils.isAllBlank(headerToken, paramToken)) {
            return unAuth(resp, "缺失令牌,鉴权失败");
        }
        String auth = StringUtils.isBlank(headerToken) ? paramToken : headerToken;
        String token = JwtUtil.getToken(auth);
        Claims claims = JwtUtil.parseJWT(token);
        if (claims == null) {
            return unAuth(resp, "请求未授权");
        }
        return chain.filter(exchange);
    }

    private boolean isSkip(String path) {
        return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains)
                || authProperties.getSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains);
    }

    private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(ResponseProvider.unAuth(msg));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }

}
