package org.springclouddev.iot.common.sdk.init;

import org.springclouddev.iot.common.sdk.service.DriverCommonService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Driver SDK Initial
 *

 */
@Component
@EnableFeignClients(basePackages = {
        "com.dc3.api.center.manager.*"
})
@ComponentScan(basePackages = {
        "com.dc3.api.center.manager",
        "com.dc3.common.sdk"
})
public class DriverInitRunner implements ApplicationRunner {

    @Resource
    private DriverCommonService driverCommonService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        driverCommonService.initial();
    }
}
