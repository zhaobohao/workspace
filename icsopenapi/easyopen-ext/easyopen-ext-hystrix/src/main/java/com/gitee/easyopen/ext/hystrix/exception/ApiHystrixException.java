package com.gitee.easyopen.ext.hystrix.exception;

import com.gitee.easyopen.exception.ApiException;

/**
 * @author tanghc
 */
public class ApiHystrixException extends ApiException {
    public ApiHystrixException(String msg, String code) {
        super(msg, code);
    }
}
