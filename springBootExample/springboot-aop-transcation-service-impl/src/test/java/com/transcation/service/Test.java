package com.transcation.service;

import com.transcation.layout.executor.Layout;
import com.transcation.layout.instance.ServiceInstance;


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

        service1.setParam(deWorkBaseServiceContext);
        service2.setParam(deWorkBaseServiceContext);

        Layout.beginWork(35000000, service0);

        // 展示最后一个Service的运行结果
        //System.out.println(service2.getserviceResult());
        Layout.shutDown();
    }
}
