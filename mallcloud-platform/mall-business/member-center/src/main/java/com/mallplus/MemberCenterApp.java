package com.mallplus;

import com.mallplus.common.annotation.EnableLoginArgResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**

 */
@EnableLoginArgResolver
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.mallplus.*.mapper")
public class MemberCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(MemberCenterApp.class, args);
    }
}
