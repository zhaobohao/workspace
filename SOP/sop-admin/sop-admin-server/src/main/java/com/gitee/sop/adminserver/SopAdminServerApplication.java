package com.gitee.sop.adminserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SopAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SopAdminServerApplication.class, args);
	}
}
