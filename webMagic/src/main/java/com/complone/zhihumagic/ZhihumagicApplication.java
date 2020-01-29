package com.complone.zhihumagic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.complone.zhihumagic.mapper")
@EnableAsync
public class ZhihumagicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihumagicApplication.class, args);
    }
}
