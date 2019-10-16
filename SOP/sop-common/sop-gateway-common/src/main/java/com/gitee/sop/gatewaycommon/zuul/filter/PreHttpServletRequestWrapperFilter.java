package com.gitee.sop.gatewaycommon.zuul.filter;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * 包装一下Request，使得request.getInputStream()方法可以调用多次
 * @author tanghc
 */
public class PreHttpServletRequestWrapperFilter extends BaseZuulFilter {
    @Override
    protected FilterType getFilterType() {
        return FilterType.PRE;
    }

    @Override
    protected int getFilterOrder() {
        return HTTP_SERVLET_REQUEST_WRAPPER_FILTER_ORDER;
    }

    @Override
    protected Object doRun(RequestContext requestContext) throws ZuulException {
        HttpServletRequest request = requestContext.getRequest();
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request);
        requestContext.setRequest(wrapper);
        return null;
    }
}
