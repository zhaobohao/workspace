package com.gitee.sop.gatewaycommon.zuul.controller;

import com.gitee.sop.gatewaycommon.bean.BaseErrorLogController;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tanghc
 */
@RestController
public class ErrorLogController extends BaseErrorLogController<HttpServletRequest> {

    @Override
    protected ApiParam getApiParam(HttpServletRequest request) {
        Map<String, String> params = RequestUtil.convertRequestParamsToMap(request);
        return ApiParam.build(params);
    }
}
