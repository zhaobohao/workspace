package com.xcbeyond.springboot.grpc.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springclouddev.core.launch.SpingCloudDevApplication;
@SpringBootApplication
public class SpringBootGrpcClientApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run("spring-boot-grpc-client", SpringBootGrpcClientApplication.class, args);
	}

}

