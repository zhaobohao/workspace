package com.gitee.sop.gatewaycommon.support;

import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.result.ApiResult;
import com.gitee.sop.gatewaycommon.result.JsonResult;
import com.gitee.sop.gatewaycommon.validate.taobao.TaobaoSigner;
import org.springframework.beans.factory.annotation.Value;

import java.util.function.Supplier;

/**
 * @author tanghc
 */
public abstract class SopBaseController<T> {

    TaobaoSigner signer = new TaobaoSigner();

    @Value("${sop.secret}")
    private String secret;

    protected abstract ApiParam getApiParam(T t);

    public ApiResult execute(T request, Supplier<Object> supplier) {
        try {
            this.check(request);
            JsonResult apiResult = new JsonResult();
            apiResult.setData(supplier.get());
            return apiResult;
        } catch (Exception e) {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode("505050");
            apiResult.setMsg(e.getMessage());
            return apiResult;
        }
    }

    protected void check(T request) {
        ApiParam apiParam = getApiParam(request);
        boolean right = signer.checkSign(apiParam, secret);
        if (!right) {
            throw new RuntimeException("签名校验失败");
        }
    }

}
