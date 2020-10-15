package com.dc3.common.exception;

/**
 * 自定义服务 异常
 *

 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
