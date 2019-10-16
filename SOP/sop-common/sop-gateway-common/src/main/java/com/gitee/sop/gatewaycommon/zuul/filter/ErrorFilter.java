package com.gitee.sop.gatewaycommon.zuul.filter;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * 处理来自post过滤器引起的异常
 * @author tanghc
 */
public class ErrorFilter extends SendErrorFilter {

    public static final String FAILED_FILTER = "failed.filter";

    private int filterOrder = 10;

    public ErrorFilter() {
        initFilterProcessor();
    }

    public void initFilterProcessor() {
        FilterProcessor instance = FilterProcessor.getInstance();
        if (!(instance instanceof MyFilterProcessor)) {
            FilterProcessor.setProcessor(new MyFilterProcessor());
        }
    }

    @Override
    public int filterOrder() {
        return filterOrder;
    }

    @Override
    public boolean shouldFilter() {
        // 判断：仅处理来自post过滤器引起的异常
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulFilter failedFilter = (ZuulFilter) ctx.get(FAILED_FILTER);
        return failedFilter != null && failedFilter.filterType().equals(FilterConstants.POST_TYPE);
    }

    public static class MyFilterProcessor extends FilterProcessor {
        @Override
        public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
            try {
                return super.processZuulFilter(filter);
            } catch (ZuulException e) {
                RequestContext ctx = RequestContext.getCurrentContext();
                ctx.set(FAILED_FILTER, filter);
                throw e;
            }
        }
    }

    public void setFilterOrder(int filterOrder) {
        this.filterOrder = filterOrder;
    }
}
