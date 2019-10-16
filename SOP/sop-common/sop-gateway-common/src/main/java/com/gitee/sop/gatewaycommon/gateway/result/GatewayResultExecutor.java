package com.gitee.sop.gatewaycommon.gateway.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.gitee.sop.gatewaycommon.message.Error;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.result.BaseExecutorAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Map;


/**
 * @author tanghc
 */
@Slf4j
public class GatewayResultExecutor extends BaseExecutorAdapter<ServerWebExchange, GatewayResult> {

    @Override
    public int getResponseStatus(ServerWebExchange exchange) {
        int responseStatus = HttpStatus.OK.value();
        List<String> errorCodeList = exchange.getResponse().getHeaders().get(SopConstants.X_SERVICE_ERROR_CODE);
        if (!CollectionUtils.isEmpty(errorCodeList)) {
            String errorCode = errorCodeList.get(0);
            responseStatus = Integer.valueOf(errorCode);
        }
        return responseStatus;
    }

    @Override
    public String getResponseErrorMessage(ServerWebExchange exchange) {
        String errorMsg = null;
        List<String> errorMessageList = exchange.getResponse().getHeaders().get(SopConstants.X_SERVICE_ERROR_MESSAGE);
        if (!CollectionUtils.isEmpty(errorMessageList)) {
            errorMsg = errorMessageList.get(0);
        }
        exchange.getResponse().getHeaders().remove(SopConstants.X_SERVICE_ERROR_MESSAGE);
        return errorMsg;
    }

    @Override
    public Map<String, Object> getApiParam(ServerWebExchange exchange) {
        return exchange.getAttribute(SopConstants.CACHE_REQUEST_BODY_FOR_MAP);
    }

    @Override
    public GatewayResult buildErrorResult(ServerWebExchange exchange, Throwable ex) {
        Error error;
        if (ex instanceof ApiException) {
            ApiException apiException = (ApiException) ex;
            error = apiException.getError();
        } else {
            error = ErrorEnum.ISP_UNKNOWN_ERROR.getErrorMeta().getError();
        }

        JSONObject jsonObject = (JSONObject) JSON.toJSON(error);
        String body = this.merge(exchange, jsonObject);

        return new GatewayResult(HttpStatus.OK, MediaType.APPLICATION_JSON_UTF8, body);
    }

}
