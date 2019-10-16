/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.gitee.sop.sdk.sign;


/**
 * 
 * @author runzhi
 */
public class SopSignException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private String            errCode;
    private String            errMsg;

    public SopSignException() {
        super();
    }

    public SopSignException(String message, Throwable cause) {
        super(message, cause);
    }

    public SopSignException(String message) {
        super(message);
    }

    public SopSignException(Throwable cause) {
        super(cause);
    }

    public SopSignException(String errCode, String errMsg) {
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