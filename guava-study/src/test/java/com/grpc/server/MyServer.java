package com.grpc.server;

import com.grpc.server.service.GrpcServer;

public class MyServer {
    public static void main(String[] args) throws  Exception {
        GrpcServer server=new GrpcServer();
        server.init(args);
    }
}
