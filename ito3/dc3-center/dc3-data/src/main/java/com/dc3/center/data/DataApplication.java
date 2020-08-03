package com.dc3.center.data;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 消息中心中心服务启动入口
 *
 * @author pnoker
 */
@SpringCloudApplication
public class DataApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }
}

