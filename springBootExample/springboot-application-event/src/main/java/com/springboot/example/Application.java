package com.springboot.example;

import com.springboot.example.event.EmailEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    EmailEventPublisher publisher;
    @Override
    public void run(String... args) throws Exception {
        publisher.pushlish("张三1","565792147@qq.com");
        publisher.pushlish("张三2","565792147@qq.com");
        publisher.pushlish("张三3","565792147@qq.com");
        publisher.pushlish("张三4","565792147@qq.com");
        publisher.pushlish("张三5","565792147@qq.com");
    }
}
