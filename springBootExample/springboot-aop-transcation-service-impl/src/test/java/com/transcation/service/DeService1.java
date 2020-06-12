package com.transcation.service;


import com.transcation.layout.callback.ICallback;
import com.transcation.layout.callback.IService;
import com.transcation.layout.service.ServiceInstanceResult;
import com.transcation.service.enums.ServiceStatus;


public class DeService1 implements IService<DeServiceServiceContext>, ICallback<DeServiceServiceContext> {

    @Override
    public ServiceStatus trade(DeServiceServiceContext context) {
        System.out.println("par1的入参来自于par0： " + context.getData());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setUser(new User("user1"));
        return  ServiceStatus.FAILS;
    }

    @Override
    public ServiceStatus refund(DeServiceServiceContext context) {
        System.out.println("DeService1 run refund() "+Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
        return ServiceStatus.SUCCESS;
    }

    @Override
    public ServiceStatus check(DeServiceServiceContext context) {
        System.out.println("DeService1 run check() "+Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
        return ServiceStatus.SUCCESS;
    }


    @Override
    public ServiceStatus defaultValue() {
        return ServiceStatus.FAILS;
    }

    @Override
    public void begin() {
        //System.out.println(Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
    }

    @Override
    public void result(boolean success, DeServiceServiceContext param, ServiceInstanceResult<ServiceStatus> serviceResult) {
        System.out.println("worker1 的结果是：" + serviceResult.getResult());
    }

}
