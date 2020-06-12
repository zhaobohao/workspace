package com.transcation.service;

import com.transcation.layout.executor.Layout;
import com.transcation.layout.instance.ServiceInstance;
import com.transcation.layout.service.ResultState;
import com.transcation.service.enums.ServiceStatus;
import com.transcation.service.util.ServiceUtil;


import java.util.concurrent.ExecutionException;

/**
 * 后面请求依赖于前面请求的执行结果
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DeService w = new DeService();
        DeService1 w1 = new DeService1();
        DeService2 w2 = new DeService2();
        DeServiceServiceContext deWorkBaseServiceContext=     new DeServiceServiceContext();
        deWorkBaseServiceContext.setUser(new User("user0"));
        ServiceInstance service2 =  new ServiceInstance.Builder()
                .service(w2)
                .callback(w2)
                .build();

        ServiceInstance service1 = new ServiceInstance.Builder()
                .service(w1)
                .callback(w1)
                .next(service2)
                .build();

        ServiceInstance service0 = new ServiceInstance.Builder()
                .service(w)
                .param(deWorkBaseServiceContext)
                .next(service1)
                .callback(w)
                .build();
        //忽略执行service0
        //ServiceUtil.skipTrade(service0);
        service1.setParam(deWorkBaseServiceContext);
        service2.setParam(deWorkBaseServiceContext);

    if(Layout.beginWork(35000000, service0))
    {
        // 执行成功，成功后，交易置为成功。
        System.out.println(" 交易成功！");
    }else{
        // 失败的情况，返回失败,交易设置为失败。
        System.out.println("交易失败！");
    }
        // 展示最后一个Service的运行结果
        //System.out.println(service2.getserviceResult());
        Layout.shutDown();
    }
}
