package com.gitee.sop.gatewaycommon.zuul.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptorContext;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.gitee.sop.gatewaycommon.message.Error;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.result.BaseExecutorAdapter;
import com.gitee.sop.gatewaycommon.result.ResultExecutorForZuul;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.util.Pair;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * @author tanghc
 */
@Slf4j
public class ZuulResultExecutor extends BaseExecutorAdapter<RequestContext, String> implements ResultExecutorForZuul {

    @Override
    public int getResponseStatus(RequestContext requestContext) {
        List<Pair<String, String>> bizHeaders = requestContext.getZuulResponseHeaders();

        return bizHeaders.stream()
                .filter(header -> SopConstants.X_SERVICE_ERROR_CODE.equals(header.first()))
                .map(header -> Integer.valueOf(header.second()))
                .findFirst()
                .orElse(requestContext.getResponseStatusCode());
    }

    @Override
    public String getResponseErrorMessage(RequestContext requestContext) {
        String errorMsg = getHeader(requestContext, SopConstants.X_SERVICE_ERROR_MESSAGE, (index)->{
            if (index > -1) {
                requestContext.getZuulResponseHeaders().remove(index);
            }
        });
        if (StringUtils.hasText(errorMsg)) {
            errorMsg = UriUtils.decode(errorMsg, StandardCharsets.UTF_8);
        }
        return errorMsg;
    }

    @Override
    public ApiParam getApiParam(RequestContext requestContext) {
        return ZuulContext.getApiParam();
    }

    @Override
    protected Locale getLocale(RequestContext requestContext) {
        return requestContext.getRequest().getLocale();
    }

    @Override
    protected RouteInterceptorContext getRouteInterceptorContext(RequestContext requestContext) {
        return (RouteInterceptorContext) requestContext.get(SopConstants.CACHE_ROUTE_INTERCEPTOR_CONTEXT);
    }

    @Override
    public String buildErrorResult(RequestContext requestContext, Throwable throwable) {
        Locale locale = getLocale(requestContext);
        Error error = getError(locale, throwable);
        return isMergeResult(requestContext) ? this.merge(requestContext, (JSONObject) JSON.toJSON(error))
                : JSON.toJSONString(error);
    }

    public static Error getError(Locale locale, Throwable throwable) {
        Error error = null;
        if (throwable instanceof ZuulException) {
            ZuulException ex = (ZuulException) throwable;
            Throwable cause = ex.getCause();
            if (cause instanceof ApiException) {
                ApiException apiException = (ApiException) cause;
                error = apiException.getError(locale);
            }
        } else if (throwable instanceof ApiException) {
            ApiException apiException = (ApiException) throwable;
            error = apiException.getError(locale);
        }
        if (error == null) {
            error = ErrorEnum.ISP_UNKNOWN_ERROR.getErrorMeta().getError(locale);
        }
        return error;
    }

    private String getHeader(RequestContext requestContext, String name, Consumer<Integer> after) {
        List<Pair<String, String>> bizHeaders = requestContext.getZuulResponseHeaders();
        int index = -1;
        String value = null;
        for (int i = 0; i < bizHeaders.size(); i++) {
            Pair<String, String> header = bizHeaders.get(i);
            if (name.equals(header.first())) {
                value = header.second();
                index = i;
                break;
            }
        }
        if (after != null) {
            after.accept(index);
        }
        return value;
    }
}
