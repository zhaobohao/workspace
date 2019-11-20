/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.gitee.sop.test.pab;


/**
 * 
 * @author runzhi
 */
public class PabApiException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private String            errCode;
    private String            errMsg;

    public PabApiException() {
        super();
    }

    public PabApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public PabApiException(String message) {
        super(message);
    }

    public PabApiException(Throwable cause) {
        super(cause);
    }

    public PabApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

}