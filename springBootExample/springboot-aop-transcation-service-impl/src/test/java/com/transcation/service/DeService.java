package com.transcation.service;


import com.transcation.layout.callback.ICallback;
import com.transcation.layout.callback.IService;
import com.transcation.layout.service.ServiceInstanceResult;
import com.transcation.service.enums.ServiceStatus;

/**
 *
 */
public class DeService implements IService<DeServiceServiceContext>, ICallback<DeServiceServiceContext> {

    @Override
    public ServiceStatus trade(DeServiceServiceContext context) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setUser(new User("user0"));
        return  ServiceStatus.SUCCESS;
    }

    @Override
    public ServiceStatus refund(DeServiceServiceContext context) {
        System.out.println("DeWorker0 run refund() "+Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
        return ServiceStatus.SUCCESS;
    }

    @Override
    public ServiceStatus check(DeServiceServiceContext context) {
        System.out.println("DeWorker0 run check() "+Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
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
        System.out.println("worker0 的结果是：" + serviceResult.getResult());
    }

}
