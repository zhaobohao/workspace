package org.springexample.statemachine;

import ch.qos.logback.core.spi.ContextAware;
import org.springexample.statemachine.core.StateMachine;
import org.springexample.statemachine.core.configurer.StateMachineConfigurer;
import org.springexample.statemachine.core.context.MessageHeaders;
import org.springexample.statemachine.core.factory.StateMachineFactory;
import org.springexample.statemachine.service.StateMachineServiceImpl;
import org.springexample.statemachine.sf.enumerate.GrantEvent;
import org.springexample.statemachine.sf.enumerate.GrantState;
import org.springexample.statemachine.sf.statemachine.GrantStateMachineConfig;
import org.springexample.statemachine.workflow.model.StateMachineConstant;
import org.springexample.statemachine.workflow.model.StateMachineTask;
import org.springexample.statemachine.workflow.model.TaskStatus;
import org.springexample.statemachine.workflow.service.StateMachineService;
import org.springexample.statemachine.workflow.service.StateMachineTaskService;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaobo on 2016/3/20
 */
@SpringBootApplication
public class Application  {

    public static void main(String[] args) {
       SpringApplication.run(Application.class);
    }



}
