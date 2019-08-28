package com.nacos.demo;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching
@EnableWebMvc
@NacosConfigurationProperties(dataId = "id",autoRefreshed = true)
@Slf4j
/**
 * sprinb boot web 的入口，上面是常用的注解
 */
public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        // 启动Spring Boot项目的唯一入口
        SpringApplication application = new SpringApplication(
                DemoApplication.class);
        ApplicationContext ctx=application.run(args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            log.warn("Spring Boot 使用profile为:{}" , profile);
        }
        log.warn("DemoApplication is success!");

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoApplication.class);
    }


}
