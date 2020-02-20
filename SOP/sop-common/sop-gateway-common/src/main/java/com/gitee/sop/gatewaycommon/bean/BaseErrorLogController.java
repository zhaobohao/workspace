package com.gitee.sop.gatewaycommon.bean;

import com.gitee.sop.gatewaycommon.manager.ServiceErrorManager;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.result.ApiResult;
import com.gitee.sop.gatewaycommon.result.JsonResult;
import com.gitee.sop.gatewaycommon.validate.taobao.TaobaoSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

/**
 * @author tanghc
 */
public abstract class BaseErrorLogController<T> {

    TaobaoSigner signer = new TaobaoSigner();

    @Value("${sop.secret}")
    private String secret;

    protected abstract ApiParam getApiParam(T t);

    @GetMapping("/sop/listErrors")
    public ApiResult listErrors(T request) {
        try {
            this.check(request);
            ServiceErrorManager serviceErrorManager = ApiConfig.getInstance().getServiceErrorManager();
            Collection<ErrorEntity> allErrors = serviceErrorManager.listAllErrors();
            JsonResult apiResult = new JsonResult();
            apiResult.setData(allErrors);
            return apiResult;
        } catch (Exception e) {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode("505050");
            apiResult.setMsg(e.getMessage());
            return apiResult;
        }
    }

    @GetMapping("/sop/clearErrors")
    public ApiResult clearErrors(T request) {
        try {
            this.check(request);
            ServiceErrorManager serviceErrorManager = ApiConfig.getInstance().getServiceErrorManager();
            serviceErrorManager.clear();
            return new ApiResult();
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
