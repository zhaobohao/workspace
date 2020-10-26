package org.springclouddev.crm.core.exception;

/**
 * @author zhangzhiwei
 * 无权访问异常
 */
public class AuthException extends RuntimeException {

    public AuthException(){
        super();
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
