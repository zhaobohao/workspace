package com.grpc.server.service;

import cn.hutool.log.StaticLog;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    private int port = 23333;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new HelloService())
                .build().start();
        StaticLog.info(("grpc service start..."));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                StaticLog.error(("shutting down gRPC server since JVM is shutting down"));
                GrpcServer.this.stop();
                StaticLog.error("gRPC server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public void init(String[] args) throws IOException, InterruptedException {
        start();
        blockUntilShutdown();
    }
}