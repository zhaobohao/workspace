

package com.dc3.gateway.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

/**
 * @author pnoker
 */
public class GatewayUtil {

    /**
     * 获取远程客户端 IP
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "X-Real-IP".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        }
        return ip;
    }
}
