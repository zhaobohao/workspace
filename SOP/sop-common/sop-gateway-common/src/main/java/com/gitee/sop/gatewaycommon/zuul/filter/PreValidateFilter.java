package com.gitee.sop.gatewaycommon.zuul.filter;

import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.ParamBuilder;
import com.gitee.sop.gatewaycommon.validate.Validator;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 前置校验
 *
 * @author tanghc
 */
public class PreValidateFilter extends BaseZuulFilter {

    @Autowired
    private ParamBuilder<RequestContext> paramBuilder;

    @Autowired
    private Validator validator;

    @Override
    protected FilterType getFilterType() {
        return FilterType.PRE;
    }

    @Override
    protected int getFilterOrder() {
        return PRE_VALIDATE_FILTER_ORDER;
    }

    @Override
    protected Object doRun(RequestContext requestContext) throws ZuulException {
        // 解析参数
        ApiParam param = paramBuilder.build(requestContext);
        ZuulContext.setApiParam(param);
        // 验证操作，这里有负责验证签名参数
        try {
            validator.validate(param);
        } catch (ApiException e) {
            log.error("验证失败，ip:{}, params:{}", param.fetchIp(), param.toJSONString(), e);
            throw e;
        } finally {
            param.fitNameVersion();
        }
        return null;
    }

}
