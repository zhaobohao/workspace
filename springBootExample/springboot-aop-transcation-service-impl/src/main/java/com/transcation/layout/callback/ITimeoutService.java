package com.transcation.layout.callback;

/**
 * @author zhaobo wrote on 2017-12-20
 * @version 1.0
 */
public interface ITimeoutService extends IService {

    long timeOut();

    boolean enableTimeOut();
}
