package org.springclouddev.iot.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;


@EnableCaching
@SpringCloudApplication
public class Water188BTcpDriverApplication {

    public static void main(String[] args) {
        SpringApplication.run(Water188BTcpDriverApplication.class, args);
    }

}
