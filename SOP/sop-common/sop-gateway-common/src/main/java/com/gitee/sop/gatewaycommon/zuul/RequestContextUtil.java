package com.gitee.sop.gatewaycommon.zuul;

import com.netflix.util.Pair;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * @author tanghc
 */
public class RequestContextUtil {

    /**
     * 获取微服务端传递过来的header
     *
     * @param name header名
     * @return 返回value，没有返回null
     */
    public static String getZuulResponseHeader(RequestContext requestContext, String name) {
        List<Pair<String, String>> zuulResponseHeaders = requestContext.getZuulResponseHeaders();
        return zuulResponseHeaders.stream()
                .filter(pair -> StringUtils.containsIgnoreCase(pair.first(), name))
                .findFirst()
                .map(Pair::second)
                .orElse(null);
    }

    /**
     * 获取微服务端的content-type
     * @return 返回content-type
     */
    public static String getZuulContentType(RequestContext requestContext) {
        return getZuulResponseHeader(requestContext, HttpHeaders.CONTENT_TYPE);
    }
}
