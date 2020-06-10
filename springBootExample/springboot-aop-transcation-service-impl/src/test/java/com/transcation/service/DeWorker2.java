package com.transcation.service;


import com.transcation.layout.callback.ICallback;
import com.transcation.layout.callback.IService;
import com.transcation.layout.worker.ServicdResult;


/**
 * @author zhaobo wrote on 2017--11-20.
 */
public class DeWorker2 implements IService<ServicdResult<User>, String>, ICallback<ServicdResult<User>, String> {

    @Override
    public String action(ServicdResult<User> result) {
        System.out.println("par2的入参来自于par1： " + result.getResult());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result.getResult().getName();
    }


    @Override
    public String defaultValue() {
        return "default";
    }

    @Override
    public void begin() {
        //System.out.println(Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
    }

    @Override
    public void result(boolean success, ServicdResult<User> param, ServicdResult<String> serviceResult) {
        System.out.println("worker2 的结果是：" + serviceResult.getResult());
    }

}
