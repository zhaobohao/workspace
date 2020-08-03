package com.dc3.center.manager.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 初始化
 *
 * @author pnoker
 */
@Component
@EnableFeignClients(basePackages = {
        "com.dc3.api.center.data.*",
})
@ComponentScan(basePackages = {
        "com.dc3.api.center.data",
})
public class ManagerInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
