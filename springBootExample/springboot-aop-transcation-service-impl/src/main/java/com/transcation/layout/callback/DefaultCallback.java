package com.transcation.layout.callback;

import com.transcation.layout.service.ServiceInstanceResult;
import com.transcation.service.base.BaseServiceContext;
import com.transcation.service.enums.ServiceStatus;

/**
 * 默认回调类，如果不设置的话，会默认给这个回调
 * @author zhaobo wrote on 2017-11-19.
 */
public class DefaultCallback<T extends BaseServiceContext> implements ICallback<T> {
    @Override
    public void begin() {
        
    }

    @Override
    public void result(boolean success, T param, ServiceInstanceResult<ServiceStatus> serviceResult) {

    }

}
