package com.gitee.sop.servercommon.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.servercommon.bean.OpenContext;
import com.gitee.sop.servercommon.bean.OpenContextImpl;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.util.OpenUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanghc
 */
public class ServiceContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServiceContext context = ServiceContext.getCurrentContext();
        context.setRequest(request);
        context.setResponse(response);
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 修复方法参数个数为0，OpenContext获取null问题
            if (handlerMethod.getMethodParameters().length == 0) {
                initOpenContext(request);
            }
        }
        return true;
    }


    /**
     * 初始化OpenContext
     *
     * @param request request
     */
    protected void initOpenContext(HttpServletRequest request) {
        JSONObject requestParams = OpenUtil.getRequestParams(request);
        OpenContext openContext = new OpenContextImpl(requestParams);
        ServiceContext.getCurrentContext().setOpenContext(openContext);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ServiceContext.getCurrentContext().unset();
    }
}
