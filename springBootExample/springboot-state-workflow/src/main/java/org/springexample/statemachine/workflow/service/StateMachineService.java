package org.springexample.statemachine.workflow.service;

import org.springexample.statemachine.core.configurer.StateMachineConfigurer;
import org.springexample.statemachine.workflow.model.StateMachineTask;

import java.util.List;

/**
 * 状态机Service
 * Created by zhaobo on 2016/4/2
 */
public interface StateMachineService {

    /**
     * 根据状态机名获取状态机配置
     * @param stateMachineName
     * @param <S>
     * @param <E>
     * @return
     */
    <S, E> StateMachineConfigurer<S, E> getByName(String stateMachineName);

    /**
     * 通过定时调度启动任务
     * 1、从数据库中将任务查询出来
     * 2、标记任务为运行中
     * 3、将任务放入到MQ中
     * 注意事务处理，忽略超时重试的场景
     * @return 返回获取到的task
     */
    List<StateMachineTask> execute();

    /**
     * 执行task
     * @param task 任务
     */
    <S, E> void processTask(StateMachineTask task);
}
