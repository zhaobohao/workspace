package com.gitee.sop.gatewaycommon.zuul.route;

import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.route.BaseForwardChooser;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.zuul.context.RequestContext;

/**
 * @author tanghc
 */
public class ZuulForwardChooser extends BaseForwardChooser<RequestContext> {

    @Override
    public ApiParam getApiParam(RequestContext requestContext) {
        return ZuulContext.getApiParam();
    }

}
