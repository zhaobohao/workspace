package com.gitee.easyopen.exception;

/**
 * @author tanghc
 */
public class LoginErrorException extends Exception {
    private static final long serialVersionUID = -6721499454527023339L;

    public LoginErrorException() {
        super();
    }

    public LoginErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginErrorException(String message) {
        super(message);
    }

    public LoginErrorException(Throwable cause) {
        super(cause);
    }

}
