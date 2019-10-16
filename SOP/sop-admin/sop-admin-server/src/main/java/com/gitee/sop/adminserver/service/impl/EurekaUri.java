package com.gitee.sop.adminserver.service.impl;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * https://github.com/Netflix/eureka/wiki/Eureka-REST-operations
 *
 * @author tanghc
 */
public enum EurekaUri {

    /**
     * 查询所有实例 Query for all instances
     */
    QUERY_APPS("GET", "/apps"),
    /**
     * 查询一个实例
     */
    QUERY_INSTANCES("GET", "/instances/%s"),
    /**
     * 下线 Take instance out of service
     */
    OFFLINE_SERVICE("PUT", "/apps/%s/%s/status?value=OUT_OF_SERVICE"),
    /**
     * 上线 Move instance back into service (remove override)
     */
    ONLINE_SERVICE("DELETE", "/apps/%s/%s/status?value=UP"),
    /**
     * 设置metadata信息
     *
     * /apps/{appID}/{instanceID}/metadata?key=value
     */
    SET_METADATA("PUT", "/apps/%s/%s/metadata?%s=%s")
    ;
    public static final String URL_PREFIX = "/";

    String uri;
    String requestMethod;

    EurekaUri(String httpMethod, String uri) {
        if (!uri.startsWith(URL_PREFIX)) {
            uri = "/" + uri;
        }
        this.uri = uri;
        this.requestMethod = httpMethod;
    }

    public String getUri(String... args) {
        if (args == null || args.length == 0) {
            return uri;
        }
        Object[] param = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            param[i] = args[i];
        }
        return String.format(uri, param);
    }

    public Request getRequest(String url, String... args) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        String requestUrl = url + getUri(args);
        Request request = this.getBuilder()
                .url(requestUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        return request;
    }

    public Request.Builder getBuilder() {
        String method = requestMethod;
        RequestBody requestBody = null;
        if (HttpMethod.requiresRequestBody(method)) {
            MediaType contentType = MediaType.parse("application/json");
            requestBody = RequestBody.create(contentType, "{}");
        }
        return new Request.Builder().method(requestMethod, requestBody);
    }
}
