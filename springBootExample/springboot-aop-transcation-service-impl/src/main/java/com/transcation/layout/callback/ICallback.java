package com.transcation.layout.callback;


import com.transcation.layout.service.ServiceInstanceResult;
import com.transcation.service.base.BaseServiceContext;
import com.transcation.service.enums.ServiceStatus;

/**
 * 每个执行单元执行完毕后，会回调该接口</p>
 * 需要监听执行结果的，实现该接口即可
 * @author zhaobo wrote on 2017-11-19.
 */
public interface ICallback<T extends  BaseServiceContext>{

    void begin();

    void result(boolean success, T param, ServiceInstanceResult<ServiceStatus> serviceResult);
}
