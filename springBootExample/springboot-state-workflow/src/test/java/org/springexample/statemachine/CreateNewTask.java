package org.springexample.statemachine;

import org.springexample.statemachine.sf.enumerate.GrantState;
import org.springexample.statemachine.workflow.model.StateMachineTask;
import org.springexample.statemachine.workflow.model.TaskStatus;
import org.springexample.statemachine.workflow.service.StateMachineService;
import org.springexample.statemachine.workflow.service.StateMachineTaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class CreateNewTask implements CommandLineRunner {
    @Resource
    StateMachineTaskService stateMachineTaskService;
    @Resource
    ApplicationContext applicationContext;
    @Override
    public void run(String... args) throws Exception {
        StateMachineService stateMachineService = applicationContext.getBean(StateMachineService.class);
        // 通用的调用模式，先将task保存到数据库里，然后取出来（为了要ID),
        // 用stateMachineService.processTask(task)来执行
        StateMachineTask task = new StateMachineTask();
        task.setMachineCode(""+System.currentTimeMillis());
        // 下面这个是你工作流的初始状态
        task.setMachineState(GrantState.WAIT_CREATE_CARDII.name());
        task.setNextRunTime(new Date());
        task.setScanStatus(TaskStatus.open.name());
        task.setCurrentTrytimes(0);
        // 下面这个是你工作流的名字
        task.setMachineType("SF");
        // 你要传的参数，写在这里面，Action可以拿到使用。
        task.setRequestData("mock模拟状态机Task");
        task.setRetryTimes(3);
        task.setTransactionId(String.valueOf(System.currentTimeMillis()));
        stateMachineTaskService.insertSelective(task);

        List<StateMachineTask> tasks = stateMachineService.execute();
        for (StateMachineTask task1 : tasks) {
            stateMachineService.processTask(task1);
        }
    }
}
