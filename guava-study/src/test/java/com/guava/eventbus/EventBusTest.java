package com.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class EventBusTest {
    @Test
    /**
     *  演示eventBus的终极用法
     *
     * 1.注册了一个Listener，使用eventBus发送消息它的父类的Subscribe也会对此消息进行处理。
     * 2.eventBus会根据Listener的参数类型的不同，分别向不同的Subscribe发送不同的消息
     * 3.当作为参数的event之间有继承关系时，使用eventBus发送消息，eventt的父类listener也会对此消息进行处理。
     * 4.
     */

    public void eventBusTest1() {
        //在默认情况下，EventBus不会对异常信息进行处理，异常信息也不会终止EventBus的运行，只会简单的打印出异常堆栈信息。
        //在EventBus构造函数中传入SubscriberExceptionHandler来对异常信息进行处理
        //下面是通过lambda表达式来实现SubscriberExceptionHandler接口
        final EventBus eventBus = new EventBus((exception, context) -> {
            System.out.println(context.getEvent());//Exception event
            System.out.println(context.getEventBus());//defalut
            System.out.println(context.getSubscriber());//ExceptionListener
            System.out.println(context.getSubscriberMethod());//m3
        });
        //i注册异常监听器,
        eventBus.register(new ExceptionListener());

        eventBus.register(new AbstractCommonListener());
        eventBus.register(new ChildCommonListener());

        //当eventBus发布一个没有listener的event，会自动DeadEvent,用DeadEventListener来接收。
        eventBus.register(new DeadEventListener());

        eventBus.post("i am s StrEvent");
        Fruit f = new Fruit();
        f.setName("waterflow");
        eventBus.post(f);

        //发布一个没有监听器的event
        eventBus.post(2);
        eventBus.post(new Apple());

    }

}



class DeadEventListener {
    @Subscribe
    public void handle(DeadEvent event){
        //获取事件源
        System.out.println(event.getSource());//DEAD-EVENT-BUS
        //获取事件
        System.out.println(event.getEvent());//DeadEventListener event
    }
}


@Log4j2
class AbstractCommonListener {

    @Subscribe
    public void commonListener(String strEvent) {
        log.info("abstractCommonListener receive a strEvent:{}", strEvent);
    }
}

@Log4j2
class ChildCommonListener extends AbstractCommonListener {
    @Subscribe
    public void childListener(String StrEvent) {
        log.info("childlistener receive a strEvent:{}", StrEvent);
    }

    @Subscribe
    public void eat(Fruit f) {
        log.info("we will eat a fruit,we eat a {}", f.getName());
    }

    @Subscribe
    public void eat(Apple a) {
        log.info("we will eat a apple ,so we eat a {}", a.getName());
    }
}

@Log4j2
@Data
class Fruit {

    public String name;

    public int sweet;
}

class Apple extends Fruit {
    public Apple() {
        this.name = "apple";
    }
}


@Log4j2
class ExceptionListener {


    @Subscribe
    public void m1(final String event) {
        if (log.isInfoEnabled()) {
            log.info("Received event [{}] and will take m1", event);
        }
    }

    @Subscribe
    public void m2(final String event) {
        if (log.isInfoEnabled()) {
            log.info("Received event [{}] and will take m2", event);
        }
    }

    @Subscribe
    public void m3(final String event) {
        throw new RuntimeException();
    }
}
