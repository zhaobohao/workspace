package com.gitee.sop.gatewaycommon.zuul.filter;

import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.ParamBuilder;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 校验工作转移到了 com.gitee.sop.gateway.controller.RedirectController
 *
 * 将校验工作提前，如果在zuul过滤器中校验，抛出异常将会打印非常多的日志，并且无法实现自定义返回结果。
 *
 * @author tanghc
 */
public class PreValidateFilter extends BaseZuulFilter {

    @Autowired
    private ParamBuilder<RequestContext> paramBuilder;

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
        ApiParam param = ZuulContext.getApiParam();
        if (param == null) {
            param = paramBuilder.build(requestContext);
            ZuulContext.setApiParam(param);
        }
        return null;
    }

}
