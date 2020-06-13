package com.transcation.service;

import com.transcation.layout.callback.ICallback;
import com.transcation.layout.callback.IService;
import com.transcation.layout.service.ServiceInstanceResult;
import com.transcation.service.enums.ServiceStatus;
import lombok.Data;

/**
 *
 */
@Data
public class DeService implements IService<DeServiceServiceContext>, ICallback<DeServiceServiceContext> {
    /**
     * trade 方法的返回值 ，为了方便测试。
     */
    private ServiceStatus tradeServiceStatus=ServiceStatus.SUCCESS;
    /**
     * refund 方法的返回值 ，为了方便测试。
     */
    private ServiceStatus refundServiceStatus=ServiceStatus.SUCCESS;
    /**
     * check 方法的返回值 ，为了方便测试。
     */
    private ServiceStatus checkServiceStatus=ServiceStatus.SUCCESS;
    @Override
    public ServiceStatus trade(DeServiceServiceContext context) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setUser(new User("user0"));
        return  tradeServiceStatus;
    }

    @Override
    public ServiceStatus refund(DeServiceServiceContext context) {
        System.out.println("DeService0 run refund() "+Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
        return  refundServiceStatus;
    }

    @Override
    public ServiceStatus check(DeServiceServiceContext context) {
        System.out.println("DeService0 run check() "+Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
        return checkServiceStatus;
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
        System.out.println("service0 的结果是：" + serviceResult.getResult());
    }

}
