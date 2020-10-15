package com.dc3.gateway.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 初始化
 *

 */
@Component
@EnableFeignClients(basePackages = {
        "com.dc3.api.center.auth.user.*",
        "com.dc3.api.center.auth.token.*",
        "com.dc3.api.center.auth.blackIp.*"
})
@ComponentScan(basePackages = {
        "com.dc3.api.center.auth.user",
        "com.dc3.api.center.auth.token",
        "com.dc3.api.center.auth.blackIp",
})
public class GatewayInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
