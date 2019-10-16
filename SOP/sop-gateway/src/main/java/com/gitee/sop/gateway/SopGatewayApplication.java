package com.gitee.sop.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

// 开启网关功能
@EnableZuulProxy
// 扫描自定义的servlet（类上标注@WebServle）
@ServletComponentScan
@SpringBootApplication
public class SopGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SopGatewayApplication.class, args);
    }

}

