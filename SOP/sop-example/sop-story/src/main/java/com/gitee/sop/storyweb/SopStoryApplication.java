package com.gitee.sop.storyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SopStoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SopStoryApplication.class, args);
    }

}

