package org.springexample.statemachine.service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springexample.statemachine.dao.mapping.StateMachineTaskMapping;
import org.springexample.statemachine.sf.enumerate.GrantState;
import org.springexample.statemachine.workflow.model.StateMachineTask;
import org.springexample.statemachine.workflow.model.TaskStatus;
import org.springexample.statemachine.workflow.service.StateMachineTaskService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaobo on 2016/4/2
 */
@Component
@Slf4j
public class StateMachineTaskServiceImpl extends ServiceImpl<StateMachineTaskMapping, StateMachineTask> implements StateMachineTaskService,IService<StateMachineTask> {

    @Resource
    StateMachineTaskMapping stateMachineTaskMapping;

    /**
     * 新增状态机
     *
     * @param task 状态机
     * @return 影响行
     */
    @Override
    public int insertSelective(StateMachineTask task) {
        log.info("新增状态机 transactionId ->{}", task.getTransactionId());
        return stateMachineTaskMapping.insert(task);
    }

    @Override
    public StateMachineTask findByCode(String code) {
        StateMachineTask stateMachineTask = new StateMachineTask();
        stateMachineTask.setMachineCode(code);
        return stateMachineTaskMapping.selectOne(stateMachineTask);
    }

    /**
     * 根据主键修改状态机任务
     *
     * @param task 状态机
     * @return 影响行
     */
    @Override
    public int updateByPrimaryKeySelective(StateMachineTask task) {
        log.info("修改状态机 transactionId ->{}", task.getTransactionId());
        return stateMachineTaskMapping.updateById(task);
    }

    /**
     * 根据机器TransactionId查询
     *
     * @param transactionId 唯一编号
     * @return 状态机
     */
    @Override
    public StateMachineTask findByTransactionId(String transactionId) {
        StateMachineTask stateMachineTask = new StateMachineTask();
        stateMachineTask.setTransactionId(transactionId);
        return stateMachineTaskMapping.selectOne(stateMachineTask);
    }

    /**
     * 获取需要执行的状态机任务
     *
     * @return 状态机任务
     */
    @Override
    public List<StateMachineTask> getExecuteTask() {
        return this.selectList(Condition.create().eq("scan_status",TaskStatus.open.name()));
    }
}
