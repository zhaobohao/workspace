package com.xcbeyond.springboot.grpc.server;

import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootGrpcServerApplication {
	public static void main(String[] args) {
		SpingCloudDevApplication.run("spring-boot-grpc-server", SpringBootGrpcServerApplication.class, args);
	}

}
