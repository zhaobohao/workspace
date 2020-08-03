package com.dc3.common.exception;

/**
 * 服务接口异常
 *
 * @author pnoker
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
