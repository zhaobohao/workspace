package com.transcation.layout.exception;

public class ServiceFailsException  extends RuntimeException {
    public ServiceFailsException() {
        super();
    }

    public ServiceFailsException(String message) {
        super(message);
    }
}