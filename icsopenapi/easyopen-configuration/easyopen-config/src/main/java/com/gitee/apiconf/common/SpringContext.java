package com.gitee.apiconf.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

    private static volatile  ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        ctx = arg0;
    }

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

}
