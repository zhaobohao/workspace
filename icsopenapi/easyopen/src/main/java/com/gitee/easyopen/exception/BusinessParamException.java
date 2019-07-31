package com.gitee.easyopen.exception;

/**
 * @author tanghc
 */
public class BusinessParamException extends ApiException {
    private static final long serialVersionUID = 1L;

    public BusinessParamException(String msg, String code) {
        super(msg, code);
    }

}
