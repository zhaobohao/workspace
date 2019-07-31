package com.gitee.easyopen.support;

import com.gitee.easyopen.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanghc
 */
public interface ResponseHandler {
    /**
     * 触发异常
     * @param e
     * @return 返回结果
     */
    Result caugthException(Throwable e);

    /**
     * 响应结果到客户端
     * @param request
     * @param response
     * @param result
     */
    void responseResult(HttpServletRequest request, HttpServletResponse response, Object result);
}
