package com.gitee.sop.gatewaycommon.gateway.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptorContext;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.message.Error;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.result.BaseExecutorAdapter;
import com.gitee.sop.gatewaycommon.result.ResultExecutorForGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;


/**
 * @author tanghc
 */
@Slf4j
public class GatewayResultExecutor extends BaseExecutorAdapter<ServerWebExchange, String>
        implements ResultExecutorForGateway {

    @Override
    public int getResponseStatus(ServerWebExchange exchange) {
        HttpStatus statusCode = exchange.getResponse().getStatusCode();
        int responseStatus = statusCode.value();
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
        if (StringUtils.hasText(errorMsg)) {
            errorMsg = UriUtils.decode(errorMsg, StandardCharsets.UTF_8);
        }
        exchange.getResponse().getHeaders().remove(SopConstants.X_SERVICE_ERROR_MESSAGE);
        return errorMsg;
    }

    @Override
    public ApiParam getApiParam(ServerWebExchange exchange) {
        return ServerWebExchangeUtil.getApiParam(exchange);
    }

    @Override
    protected Locale getLocale(ServerWebExchange exchange) {
        return exchange.getLocaleContext().getLocale();
    }

    @Override
    protected RouteInterceptorContext getRouteInterceptorContext(ServerWebExchange exchange) {
        return (RouteInterceptorContext) exchange.getAttributes().get(SopConstants.CACHE_ROUTE_INTERCEPTOR_CONTEXT);
    }

    @Override
    public String buildErrorResult(ServerWebExchange exchange, Throwable ex) {
        Locale locale = getLocale(exchange);
        Error error;
        if (ex instanceof ApiException) {
            ApiException apiException = (ApiException) ex;
            error = apiException.getError(locale);
        } else {
            error = ErrorEnum.ISP_UNKNOWN_ERROR.getErrorMeta().getError(locale);
        }
        JSONObject jsonObject = (JSONObject) JSON.toJSON(error);
        return this.merge(exchange, jsonObject);
    }

}
