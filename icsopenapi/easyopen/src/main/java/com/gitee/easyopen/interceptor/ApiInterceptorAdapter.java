package com.gitee.easyopen.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gitee.easyopen.ApiMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tanghc
 */
public abstract class ApiInterceptorAdapter implements ApiInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu,
            Object result) throws Exception {
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object serviceObj,
            Object argu, Object result, Exception e) throws Exception {
    }

    @Override
    public boolean match(ApiMeta apiMeta) {
        return true;
    }

}