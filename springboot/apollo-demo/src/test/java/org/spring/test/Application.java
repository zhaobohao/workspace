package org.spring.test;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableApolloConfig
@Slf4j
/**
 * sprinb boot web 的入口，上面是常用的注解
 */
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        // 启动Spring Boot项目的唯一入口
        SpringApplication application = new SpringApplication(
                Application.class);
        ApplicationContext ctx=application.run(args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            log.warn("Spring Boot 使用profile为:{}" , profile);
        }
        log.warn("DemoApplication is success!");

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }


}