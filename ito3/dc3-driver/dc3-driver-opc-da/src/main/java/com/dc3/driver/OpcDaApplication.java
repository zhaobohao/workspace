package com.dc3.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;


@EnableCaching
@SpringCloudApplication
public class OpcDaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpcDaApplication.class, args);
    }
}

