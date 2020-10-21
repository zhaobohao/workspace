package org.springclouddev.iot.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>设备管理服务入口
 */
@SpringCloudApplication
@EnableTransactionManagement
@EnableCaching(mode = AdviceMode.ASPECTJ)
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}

