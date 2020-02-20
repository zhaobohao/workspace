package com.gitee.sop.gatewaycommon.zuul;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.DefaultRouteInterceptorContext;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.ParamBuilder;
import com.gitee.sop.gatewaycommon.util.ResponseUtil;
import com.gitee.sop.gatewaycommon.util.RouteInterceptorUtil;
import com.gitee.sop.gatewaycommon.validate.Validator;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 负责签名校验
 * @author tanghc
 */
@Slf4j
public class ValidateService {

    @Autowired
    private ParamBuilder<RequestContext> paramBuilder;

    @Autowired
    private Validator validator;

    /**
     * 校验操作
     *
     * @param currentContext  currentContext
     * @param callback 校验后操作
     */
    public void validate(RequestContext currentContext, ValidateCallback callback) {
        // 解析参数
        ApiParam param = ZuulContext.getApiParam();
        if (param == null) {
            param = paramBuilder.build(currentContext);
            ZuulContext.setApiParam(param);
        }
        doValidate(currentContext, param, callback);
    }

    /**
     * 签名校验
     *
     * @param currentContext currentContext
     */
    private void doValidate(RequestContext currentContext, ApiParam param, ValidateCallback callback) {
        Exception error = null;
        // 验证操作，这里有负责验证签名参数
        try {
            validator.validate(param);
            this.afterValidate(currentContext, param);
        } catch (Exception e) {
            error = e;
        }
        param.fitNameVersion();
        if (callback != null) {
            if (error == null) {
                callback.onSuccess(currentContext);
            } else {
                callback.onError(currentContext, param, error);
            }
        }
    }

    private void afterValidate(RequestContext currentContext, ApiParam param) {
        RouteInterceptorUtil.runPreRoute(currentContext, param, context -> {
            DefaultRouteInterceptorContext defaultRouteInterceptorContext = (DefaultRouteInterceptorContext) context;
            defaultRouteInterceptorContext.setRequestDataSize(currentContext.getRequest().getContentLengthLong());
            currentContext.set(SopConstants.CACHE_ROUTE_INTERCEPTOR_CONTEXT, context);
        });
    }

    public interface ValidateCallback {
        /**
         * 校验成功触发
         *
         * @param currentContext 上下文
         */
        void onSuccess(RequestContext currentContext);

        /**
         * 校验失败触发
         *
         * @param currentContext 上下文
         * @param param          参数
         * @param throwable      异常
         */
        default void onError(RequestContext currentContext, ApiParam param, Throwable throwable) {
            log.error("验证失败，ip:{}, params:{}, errorMsg:{}", param.fetchIp(), param.toJSONString(), throwable.getMessage());
            String errorResult = ApiConfig.getInstance().getZuulResultExecutor().buildErrorResult(currentContext, throwable);
            ResponseUtil.writeJson(currentContext.getResponse(), errorResult);
        }
    }
}
