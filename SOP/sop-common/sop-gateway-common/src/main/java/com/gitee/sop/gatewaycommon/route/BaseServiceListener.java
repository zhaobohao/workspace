package com.gitee.sop.gatewaycommon.route;

import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @author tanghc
 */
public abstract class BaseServiceListener implements ServiceListener {

    private static final String SECRET = "a3d9sf!1@odl90zd>fkASwq";

    private static RestTemplate restTemplate = new RestTemplate();

    static {
        // 解决statusCode不等于200，就抛异常问题
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            protected boolean hasError(HttpStatus statusCode) {
                return statusCode == null;
            }
        });
    }

    protected static String buildQuery(String secret) {
        String time = String.valueOf(System.currentTimeMillis());
        String source = secret + time + secret;
        String sign = DigestUtils.md5DigestAsHex(source.getBytes());
        return "?time=" + time + "&sign=" + sign;
    }

    protected static String buildQuery() {
        return buildQuery(SECRET);
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
