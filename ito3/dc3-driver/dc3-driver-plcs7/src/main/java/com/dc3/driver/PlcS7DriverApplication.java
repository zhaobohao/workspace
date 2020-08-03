package com.dc3.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author pnoker
 */
@EnableCaching
@SpringCloudApplication
public class PlcS7DriverApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlcS7DriverApplication.class, args);
    }
}

