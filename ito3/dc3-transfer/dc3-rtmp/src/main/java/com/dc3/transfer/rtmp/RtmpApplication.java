package com.dc3.transfer.rtmp;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Rtmp 视频转码服务启动入口
 *

 */
@EnableCaching
@SpringCloudApplication
public class RtmpApplication {

    public static void main(String[] args) {
        SpringApplication.run(RtmpApplication.class, args);
    }
}

