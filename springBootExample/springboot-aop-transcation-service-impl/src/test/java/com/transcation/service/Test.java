package com.transcation.service;

import com.transcation.layout.executor.Layout;
import com.transcation.layout.worker.ServicdResult;
import com.transcation.layout.wrapper.ServiceInstance;


import java.util.concurrent.ExecutionException;

/**
 * 后面请求依赖于前面请求的执行结果
 * @author zhaobo wrote on 2017--12-26
 * @version 1.0
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DeWorker w = new DeWorker();
        DeWorker1 w1 = new DeWorker1();
        DeWorker2 w2 = new DeWorker2();

        ServiceInstance<ServicdResult<User>, String> workerWrapper2 =  new ServiceInstance.Builder<ServicdResult<User>, String>()
                .worker(w2)
                .callback(w2)
                .build();

        ServiceInstance<ServicdResult<User>, User> workerWrapper1 = new ServiceInstance.Builder<ServicdResult<User>, User>()
                .worker(w1)
                .callback(w1)
                .next(workerWrapper2)
                .build();

        ServiceInstance<String, User> workerWrapper = new ServiceInstance.Builder<String, User>()
                .worker(w)
                .param("0")
                .next(workerWrapper1)
                .callback(w)
                .build();
        //虽然尚未执行，但是也可以先取得结果的引用，作为下一个任务的入参
        ServicdResult<User> result = workerWrapper.getserviceResult();
        ServicdResult<User> result1 = workerWrapper1.getserviceResult();

        workerWrapper1.setParam(result);
        workerWrapper2.setParam(result1);

        Layout.beginWork(3500, workerWrapper);

        System.out.println(workerWrapper2.getserviceResult());
        Layout.shutDown();
    }
}
