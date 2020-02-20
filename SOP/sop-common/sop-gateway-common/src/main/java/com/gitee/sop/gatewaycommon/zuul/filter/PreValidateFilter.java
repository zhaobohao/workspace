package com.gitee.sop.gatewaycommon.zuul.filter;

import com.netflix.zuul.context.RequestContext;

/**
 * 校验工作转移到了 com.gitee.sop.gateway.controller.RedirectController
 * <p>
 * 将校验工作提前，如果在zuul过滤器中校验，抛出异常将会打印非常多的日志，并且无法实现自定义返回结果。
 * @deprecated  see {@link com.gitee.sop.gatewaycommon.zuul.ValidateService}
 * @author tanghc
 */
@Deprecated
public class PreValidateFilter extends BaseZuulFilter {

    @Override
    protected FilterType getFilterType() {
        return FilterType.PRE;
    }

    @Override
    protected int getFilterOrder() {
        return PRE_VALIDATE_FILTER_ORDER;
    }

    @Override
    protected Object doRun(RequestContext requestContext) {
        return null;
    }

}
