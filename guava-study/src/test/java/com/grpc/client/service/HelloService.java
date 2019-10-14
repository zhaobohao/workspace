package com.grpc.client.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.acupt.grpc.proto.InvokeRequest;
import com.acupt.grpc.proto.InvokeResponse;
import com.google.common.util.concurrent.ListenableFuture;
import com.grpc.pool.GrpcClient;
import com.grpc.pool.GrpcClientPool;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HelloService {

    private static boolean done;

    public void hello(String name) throws InterruptedException, ExecutionException, TimeoutException {
        GrpcClient client = GrpcClientPool.borrowObject();
        //演示三种stub的调用方式
        try {
            InvokeRequest request = InvokeRequest.newBuilder().setName(name).build();
            //1. block stub
            InvokeResponse response = client.getHelloServiceBlockingStub().hello(request);
            StaticLog.info(StrUtil.toString(response));


            //2.future stab
            ListenableFuture<InvokeResponse> future = client.getHelloServiceFutureStub().hello(request);
            future.addListener(
                    () -> System.out.println("listener 1"),
                    command -> {
                        System.out.println("execute 1 " + command);
                        command.run();
                    });
            future.addListener(
                    () -> System.out.println("listener 2"),
                    command -> {
                        System.out.println("execute 2 " + command);
                        command.run();
                    });

            System.out.println(future.get(10, TimeUnit.SECONDS));


            //3. call back stub
            HelloService.done = false;
            client.getHelloServiceStub().hello(request, new StreamObserver<InvokeResponse>() {
                @Override
                public void onNext(InvokeResponse value) {
                    System.out.println("onNext " + value);
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println("onError " + t.getMessage());
                    t.printStackTrace();
                    HelloService.done = true;
                }

                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                    HelloService.done = true;
                }
            });
            while (!this.done) {
                Thread.sleep(1000);
            }
        } finally {
            GrpcClientPool.returnObject(client);
        }


    }


}
