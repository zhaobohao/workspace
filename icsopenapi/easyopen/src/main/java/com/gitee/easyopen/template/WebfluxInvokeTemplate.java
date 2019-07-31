package com.gitee.easyopen.template;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.support.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanghc
 */
public class WebfluxInvokeTemplate extends InvokeTemplate {

    public WebfluxInvokeTemplate(ApiConfig apiConfig, ResponseHandler responseHandler) {
        super(apiConfig, responseHandler);
    }

    @Override
    protected void afterInvoke(HttpServletRequest request, HttpServletResponse response, Object result) {
    }
}