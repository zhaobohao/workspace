package com.grpc.pool;

import com.acupt.grpc.proto.HelloServiceGrpc;
import com.grpc.client.service.HelloService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class GrpcClient {
    public static String host = "localhost";
    private final ManagedChannel channel;
    private final HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;
    private final HelloServiceGrpc.HelloServiceFutureStub helloServiceFutureStub;
    private final  HelloServiceGrpc.HelloServiceStub helloServiceStub;

    public GrpcClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
        helloServiceFutureStub=HelloServiceGrpc.newFutureStub(channel);
        helloServiceStub =HelloServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
    }

    public HelloServiceGrpc.HelloServiceBlockingStub getHelloServiceBlockingStub() {
        return helloServiceBlockingStub;
    }
    public HelloServiceGrpc.HelloServiceFutureStub getHelloServiceFutureStub(){
        return helloServiceFutureStub;
    }
    public HelloServiceGrpc.HelloServiceStub getHelloServiceStub()
    {
        return  helloServiceStub;
    }
}