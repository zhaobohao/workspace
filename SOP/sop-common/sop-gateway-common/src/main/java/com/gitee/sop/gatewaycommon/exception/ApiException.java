package com.gitee.sop.gatewaycommon.exception;


import com.gitee.sop.gatewaycommon.message.Error;
import com.gitee.sop.gatewaycommon.message.ErrorFactory;
import com.gitee.sop.gatewaycommon.message.ErrorMeta;

import java.util.Locale;

/**
 * @author tanghc
 */
public class ApiException extends RuntimeException {

    private transient Error error;

    private transient ErrorMeta errorMeta;
    private transient Object[] params;

    public ApiException(ErrorMeta errorMeta, Object... params) {
        this.errorMeta = errorMeta;
        this.params = params;
    }

    public ApiException(Throwable cause, ErrorMeta errorMeta, Object... params) {
        super(cause);
        this.errorMeta = errorMeta;
        this.params = params;
    }

    public Error getError(Locale locale) {
        if (error == null) {
            error = ErrorFactory.getError(this.errorMeta, locale, params);
        }
        return error;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return message == null ? errorMeta.toString() : message;
    }

}
