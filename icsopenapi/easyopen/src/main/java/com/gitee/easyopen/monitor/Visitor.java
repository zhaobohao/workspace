package com.gitee.easyopen.monitor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanghc
 */
public interface Visitor {
    /**
     * 访问
     * @param request
     * @param serviceObj service对象
     * @param argu 方法参数对象
     */
    void in(HttpServletRequest request, Object serviceObj, Object argu);
    /**
     * 离开
     * @param request
     * @param serviceObj service对象
     * @param argu 方法参数对象
     * @param result 返回结果
     * @param e 异常,如果出错,不为null
     */
    void out(HttpServletRequest request, Object serviceObj, Object argu, Object result, Exception e);
}
