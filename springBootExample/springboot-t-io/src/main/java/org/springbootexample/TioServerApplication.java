package org.springbootexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.core.starter.annotation.EnableTioServerServer;

/**
 * t-io usages
 * @author zhaobo
 */
@SpringBootApplication
@EnableTioServerServer
public class TioServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TioServerApplication.class, args);
    }
}
