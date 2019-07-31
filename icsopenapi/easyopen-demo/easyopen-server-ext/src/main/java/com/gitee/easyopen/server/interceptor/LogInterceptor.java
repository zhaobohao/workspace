package com.gitee.easyopen.server.interceptor;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.interceptor.ApiInterceptorAdapter;
import com.gitee.easyopen.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

public class LogInterceptor extends ApiInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception {
        logger.info("收到客户端请求,ip={},参数={}", RequestUtil.getClientIP(request), URLDecoder.decode(ApiContext.getApiParam().toJSONString(), "UTF-8"));
        return true;
    }
}
