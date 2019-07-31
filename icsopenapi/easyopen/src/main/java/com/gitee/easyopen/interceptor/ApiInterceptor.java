package com.gitee.easyopen.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gitee.easyopen.ApiMeta;

/**
 * 拦截器，原理同springmvc拦截器
 * @author tanghc
 *
 */
public interface ApiInterceptor {
    /**
     * 预处理回调方法，在方法调用前执行。返回false不继续向下执行，此时可使用response返回错误信息
     * @param request
     * @param response
     * @param serviceObj service类
     * @param argu 方法参数
     * @return 返回false不继续向下执行，此时可使用response返回错误信息
     * @throws Exception
     */
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception;

    /**
     * 接口方法执行完后调用此方法。
     * @param request
     * @param response
     * @param serviceObj service类
     * @param argu 参数
     * @param result 方法返回结果
     * @throws Exception
     */
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu,
            Object result) throws Exception;

    /**
     * 结果包装完成后执行
     * @param request
     * @param response
     * @param serviceObj service类
     * @param argu 参数
     * @param result 最终结果，被包装过
     * @param e 
     * @throws Exception
     */
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu,
            Object result, Exception e) throws Exception;

    /**
     * 匹配拦截器，返回true则执行该拦截器，否则忽略该拦截器
     * @param apiMeta 接口信息
     * @return 返回true，使用该拦截器，返回false跳过不使用。
     */
    boolean match(ApiMeta apiMeta);
}
