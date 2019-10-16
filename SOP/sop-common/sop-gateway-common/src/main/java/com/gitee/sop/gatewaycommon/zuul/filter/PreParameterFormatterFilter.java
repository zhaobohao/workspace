package com.gitee.sop.gatewaycommon.zuul.filter;

import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.param.ParameterFormatter;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.gitee.sop.gatewaycommon.zuul.param.ZuulParameterUtil;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 参数格式化过滤器，动态修改参数，此过滤器放在前面校验后面
 *
 * @author tanghc
 */
public class PreParameterFormatterFilter extends BaseZuulFilter {

    @Autowired(required = false)
    private ParameterFormatter parameterFormatter;

    @Override
    protected FilterType getFilterType() {
        return FilterType.PRE;
    }

    @Override
    protected int getFilterOrder() {
        return PRE_PARAMETER_FORMATTER_FILTER_ORDER;
    }

    @Override
    protected Object doRun(RequestContext requestContext) throws ZuulException {
        ApiParam apiParam = ZuulContext.getApiParam();
        // 校验成功后进行参数转换
        if (parameterFormatter != null) {
            ZuulParameterUtil.format(apiParam, parameterFormatter::format);
            requestContext.addZuulRequestHeader(ParamNames.HEADER_VERSION_NAME, apiParam.fetchVersion());
        }
        return null;
    }
}
