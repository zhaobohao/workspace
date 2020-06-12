package com.transcation.service.util;

import com.transcation.layout.instance.ServiceInstance;
import com.transcation.layout.service.ResultState;
import com.transcation.service.enums.ServiceStatus;

/**
 * 对serviceInstance对象的一些状态变更 方法封装
 */
public class ServiceUtil {

    /**
     * 忽略执行当前serviceInstance的trade()方法
     * @param service0
     */
    public static void skipTrade(ServiceInstance service0)
    {
        // 将一个Service的resultState设置为非default值 ，result为serviceStatus.SUCCESS则不执行这个service的trade()方法。
        service0.getserviceResult().setResultState(ResultState.SUCCESS);
        service0.getserviceResult().setResult(ServiceStatus.SUCCESS);
    }
}
