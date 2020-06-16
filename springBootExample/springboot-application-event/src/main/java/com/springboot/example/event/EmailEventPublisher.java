package com.springboot.example.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EmailEventPublisher {
    @Autowired
    private ApplicationContext applicationContext;

    public void pushlish(String msg,String mail){
        applicationContext.publishEvent(new EmailEvent(this, msg,mail));
    }

}
