package com.gitee.sop.gatewaycommon.zuul.filter;

import org.springframework.cloud.netflix.zuul.filters.pre.FormBodyWrapperFilter;

/**
 * @author tanghc
 */
public class FormBodyWrapperFilterExt extends FormBodyWrapperFilter {
    @Override
    public int filterOrder() {
        return BaseZuulFilter.FORM_BODY_WRAPPER_FILTER_ORDER;
    }

}
