package com.gitee.sop.gatewaycommon.easyopen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.message.Error;
import com.gitee.sop.gatewaycommon.result.ApiResult;
import com.gitee.sop.gatewaycommon.result.ResultExecutor;
import com.gitee.sop.gatewaycommon.zuul.result.ZuulResultExecutor;
import com.netflix.zuul.context.RequestContext;

import java.util.Optional;

/**
 * @author tanghc
 */
public class EasyopenResultExecutor implements ResultExecutor<RequestContext, String> {

    boolean onlyReturnData;

    public EasyopenResultExecutor(boolean onlyReturnData) {
        this.onlyReturnData = onlyReturnData;
    }

    @Override
    public String mergeResult(RequestContext request, String serviceResult) {
        if (onlyReturnData) {
            JSONObject jsonObject = JSON.parseObject(serviceResult);
            return Optional.ofNullable(jsonObject.getString("data")).orElse("{}");
        } else {
            return serviceResult;
        }
    }

    @Override
    public String buildErrorResult(RequestContext request, Throwable ex) {
        ApiResult apiResult = new ApiResult();
        Error error = ZuulResultExecutor.getError(ex);
        apiResult.setCode(error.getSub_code());
        apiResult.setMsg(error.getSub_msg());
        return JSON.toJSONString(apiResult);
    }
}
