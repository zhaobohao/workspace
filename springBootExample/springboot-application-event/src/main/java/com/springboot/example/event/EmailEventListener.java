package com.springboot.example.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 定义一个事件监听类
 * @author zhaobo
 *
 */
@Component
public class EmailEventListener implements ApplicationListener<EmailEvent> {

    //使用注解@Async支持 这样不仅可以支持通过调用，也支持异步调用，非常的灵活，
    //@Async
    @Override
    public void onApplicationEvent(EmailEvent event) {
        System.out.println("注册成功，发送确认邮件为：" + event.getEmail()+",消息摘要为:"+event.getMsg());
    }

}
