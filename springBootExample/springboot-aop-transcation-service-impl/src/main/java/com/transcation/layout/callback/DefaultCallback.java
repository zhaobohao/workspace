package com.transcation.layout.callback;


import com.transcation.layout.worker.ServicdResult;

/**
 * 默认回调类，如果不设置的话，会默认给这个回调
 * @author zhaobo wrote on 2017-11-19.
 */
public class DefaultCallback<T, V> implements ICallback<T, V> {
    @Override
    public void begin() {
        
    }

    @Override
    public void result(boolean success, T param, ServicdResult<V> serviceResult) {

    }

}
