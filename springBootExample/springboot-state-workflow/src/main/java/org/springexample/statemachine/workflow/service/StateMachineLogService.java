package org.springexample.statemachine.workflow.service;

import org.springexample.statemachine.workflow.model.StateMachineLog;

/**
 * 状态机日志记录
 */
public interface StateMachineLogService {

    int insertSelective(StateMachineLog record);

}