package com.gitee.easyopen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 写数据到客户端
 * 
 * @author tanghc
 *
 */
public interface RespWriter {
    
    /**
     * 输出对象到客户端
     * @param request
     * @param response
     * @param result 返回对象
     */
    void write(HttpServletRequest request, HttpServletResponse response, Object result);
}
