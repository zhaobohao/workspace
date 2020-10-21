package org.springclouddev.iot.manager.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 初始化
 */
@Component
@EnableFeignClients(basePackages = {
        "org.springclouddev.iot.data.*",
})
@ComponentScan(basePackages = {
        "org.springclouddev.iot.data",
})
public class ManagerInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
