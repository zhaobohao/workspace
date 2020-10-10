package com.dc3.common.exception;

/**
 * 自定义未授权 异常
 *
 * @author pnoker
 */
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}