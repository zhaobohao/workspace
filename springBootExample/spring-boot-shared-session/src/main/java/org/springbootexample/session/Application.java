package org.springbootexample.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
/**
 * @Author zhaobo
 */
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
