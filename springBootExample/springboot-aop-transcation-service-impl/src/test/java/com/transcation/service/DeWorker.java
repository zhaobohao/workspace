package com.transcation.service;


import com.transcation.layout.callback.ICallback;
import com.transcation.layout.callback.IService;
import com.transcation.layout.worker.ServicdResult;

/**
 * @author zhaobo wrote on 2017--11-20.
 */
public class DeWorker implements IService<String, User>, ICallback<String, User> {

    @Override
    public User action(String object) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User("user0");
    }


    @Override
    public User defaultValue() {
        return new User("default User");
    }

    @Override
    public void begin() {
        //System.out.println(Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
    }

    @Override
    public void result(boolean success, String param, ServicdResult<User> serviceResult) {
        System.out.println("worker0 的结果是：" + serviceResult.getResult());
    }

}
