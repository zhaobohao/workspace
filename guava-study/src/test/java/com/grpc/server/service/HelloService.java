package com.grpc.server.service;

import com.acupt.grpc.proto.HelloServiceGrpc;
import com.acupt.grpc.proto.InvokeRequest;
import com.acupt.grpc.proto.InvokeResponse;

public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {
    public void hello(InvokeRequest request,
                      io.grpc.stub.StreamObserver<InvokeResponse> responseObserver) {
       //实现你自己的业务逻辑
        System.out.println("request -> " + request);
        String name = request.getName();//自定义的字段名 name
        InvokeResponse response = InvokeResponse.newBuilder()
                .setMsg("hello," + name)//自定义的字段名 msg
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
