package com.transcation.layout.callback;

import com.transcation.layout.instance.ServiceInstance;

import java.util.List;

/**
 * 如果是异步执行整组的话，可以用这个组回调。不推荐使用
 * @author zhaobo wrote on 2017-11-19.
 */
public interface IGroupCallback {

    void success(List<ServiceInstance> workerWrappers);

    void failure(List<ServiceInstance> workerWrappers, Exception e);
}
