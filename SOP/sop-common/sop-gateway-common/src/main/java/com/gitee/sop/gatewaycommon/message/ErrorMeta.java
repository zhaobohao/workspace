package com.gitee.sop.gatewaycommon.message;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.netflix.zuul.context.RequestContext;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 错误对象
 *
 * @author tanghc
 */
@Getter
public class ErrorMeta {

    private static final Locale ZH_CN = Locale.SIMPLIFIED_CHINESE;

    private String modulePrefix;
    private String code;
    private String subCode;

    public ErrorMeta(String modulePrefix, String code, String subCode) {
        this.modulePrefix = modulePrefix;
        this.code = code;
        this.subCode = subCode;
    }

    public Error getError() {
        return ErrorFactory.getError(this, ZH_CN);
    }

    /**
     * 返回网关exception
     *
     * @param params 参数
     * @return 返回exception
     */
    public ApiException getException(Object... params) {
        Locale locale = getLocale();
        if (params != null && params.length == 1) {
            Object param = params[0];
            if (param instanceof Throwable) {
                Error error = ErrorFactory.getError(this, ZH_CN);
                return new ApiException((Throwable) param, error);
            }
            if (param instanceof Locale) {
                locale = (Locale) param;
            }
        }
        Error error = ErrorFactory.getError(this, locale, params);
        return new ApiException(error);
    }

    protected Locale getLocale() {
        if (ApiConfig.getInstance().isUseGateway()) {
            return ZH_CN;
        }
        RequestContext currentContext = RequestContext.getCurrentContext();
        if (currentContext == null) {
            return ZH_CN;
        }
        HttpServletRequest request = currentContext.getRequest();
        return request == null ? ZH_CN : request.getLocale();
    }

}
