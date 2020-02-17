package com.xcbeyond.springboot.grpc.server;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.xcbeyond.springboot.grpc.lib.SeeGrpc;
import com.xcbeyond.springboot.grpc.lib.SeeHelloReply;
import com.xcbeyond.springboot.grpc.lib.SeeHelloRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @Auther: xcbeyond
 * @Date: 2019/3/6 18:15
 */
@GrpcService
public class SeeWorldGrpcServer extends SeeGrpc.SeeImplBase {

    @Override
    @SentinelResource(value = "seeHello")
    public void seeHello(SeeHelloRequest request, StreamObserver<SeeHelloReply> responseObserver) {
        System.out.println("GrpcOtherServerService...");
        SeeHelloReply reply = SeeHelloReply.newBuilder().setMessage("See ==> " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}