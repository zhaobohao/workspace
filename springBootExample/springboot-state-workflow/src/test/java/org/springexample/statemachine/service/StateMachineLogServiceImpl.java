package org.springexample.statemachine.service;

import cn.hutool.core.bean.BeanUtil;
import org.springexample.statemachine.dao.mapping.StateMachineLogMapping;
import org.springexample.statemachine.workflow.model.StateMachineLog;
import org.springexample.statemachine.workflow.service.StateMachineLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by zhaobo on 2016/4/2
 */
@Slf4j
@Component
public class StateMachineLogServiceImpl implements StateMachineLogService {
    @Resource
    StateMachineLogMapping stateMachineLogMapping;
    @Override
    public int insertSelective(StateMachineLog record) {
        log.info("插入状态机日志 StateMachineCode ->{}", record.getMachineCode());
        stateMachineLogMapping.insert(record);
        return 1;
    }
}
