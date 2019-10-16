package com.gitee.sop.gatewaycommon.zuul.filter;

import org.springframework.cloud.netflix.zuul.filters.pre.Servlet30WrapperFilter;

/**
 * @author tanghc
 */
public class Servlet30WrapperFilterExt extends Servlet30WrapperFilter {
    @Override
    public int filterOrder() {
        return BaseZuulFilter.SERVLET_30_WRAPPER_FILTER_ORDER;
    }

}
