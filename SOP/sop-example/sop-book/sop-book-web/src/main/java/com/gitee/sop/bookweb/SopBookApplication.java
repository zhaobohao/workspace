package com.gitee.sop.bookweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// 允许调用其他服务
@EnableFeignClients
// 服务注册
@EnableDiscoveryClient
@SpringBootApplication
public class SopBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SopBookApplication.class, args);
    }

}

