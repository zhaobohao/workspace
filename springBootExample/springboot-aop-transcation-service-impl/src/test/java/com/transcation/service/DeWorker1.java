package com.transcation.service;


import com.transcation.layout.callback.ICallback;
import com.transcation.layout.callback.IService;
import com.transcation.layout.worker.ServicdResult;


/**
 * @author zhaobo wrote on 2017--11-20.
 */
public class DeWorker1 implements IService<ServicdResult<User>, User>, ICallback<ServicdResult<User>, User> {

    @Override
    public User action(ServicdResult<User> result) {
        System.out.println("par1的入参来自于par0： " + result.getResult());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User("user1");
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
    public void result(boolean success, ServicdResult<User> param, ServicdResult<User> serviceResult) {
        System.out.println("worker1 的结果是：" + serviceResult.getResult());
    }

}
