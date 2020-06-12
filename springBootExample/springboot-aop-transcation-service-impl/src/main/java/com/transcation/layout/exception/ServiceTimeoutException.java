package com.transcation.layout.exception;

public class ServiceTimeoutException  extends RuntimeException {
    public ServiceTimeoutException() {
        super();
    }

    public ServiceTimeoutException(String message) {
        super(message);
    }
}