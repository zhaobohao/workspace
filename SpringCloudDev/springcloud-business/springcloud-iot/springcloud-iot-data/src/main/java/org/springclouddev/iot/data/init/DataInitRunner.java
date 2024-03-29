package org.springclouddev.iot.data.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@Component
@EnableFeignClients(basePackages = {
        "com.dc3.api.center.manager.*"
})
@ComponentScan(basePackages = {
        "com.dc3.api.center.manager",
        "com.dc3.api.center.date"
})
public class DataInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
