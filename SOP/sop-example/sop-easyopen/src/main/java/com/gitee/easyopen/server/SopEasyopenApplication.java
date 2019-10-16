package com.gitee.easyopen.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SopEasyopenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SopEasyopenApplication.class, args);
	}
}
