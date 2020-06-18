package org.springexample.statemachine.core.context;

public interface Message<T> {

    T getPayload();

    MessageHeaders getHeaders();
}