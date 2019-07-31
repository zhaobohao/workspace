package com.gitee.easyopen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理请求
 * @author tanghc
 *
 */
public interface Invoker {
    
    /**
     * 调用接口方法
     * 
     * @param request
     * @param response
     * @return 返回最终业务结果
     * @throws Throwable 
     */
    Object invoke(HttpServletRequest request, HttpServletResponse response) throws Throwable;

    /**
     * 调用接口方法，返回mock数据
     * @param request
     * @param response
     * @return 返回mock数据
     * @throws Throwable
     */
    Object invokeMock(HttpServletRequest request, HttpServletResponse response) throws Throwable;
}
