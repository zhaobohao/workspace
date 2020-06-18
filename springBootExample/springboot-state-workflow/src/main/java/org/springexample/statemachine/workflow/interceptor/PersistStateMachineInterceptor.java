package org.springexample.statemachine.workflow.interceptor;

import org.springexample.statemachine.core.StateMachine;
import org.springexample.statemachine.core.context.Message;
import org.springexample.statemachine.core.context.MessageHeaders;
import org.springexample.statemachine.core.interceptor.AbstractStateMachineInterceptor;
import org.springexample.statemachine.core.state.State;
import org.springexample.statemachine.core.transition.Transition;
import org.springexample.statemachine.workflow.model.StateMachineConstant;
import org.springexample.statemachine.workflow.model.StateMachineLog;
import org.springexample.statemachine.workflow.model.StateMachineTask;
import org.springexample.statemachine.workflow.service.StateMachineLogService;
import org.springexample.statemachine.workflow.service.StateMachineTaskService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 持久化拦截器，状态发生改变后把当前状态信息持久化
 *
 * @author zhaobo
 */
@Slf4j
@Component
public class PersistStateMachineInterceptor<S, E> extends AbstractStateMachineInterceptor<S, E> {

    //加上action唯一执行key，方便日志查看
    public static final String TRANSITION_UNIQUE_ID = "transition_unique_id";
    public static final int MID_TEXT_LENGTH = 1677721;
    @Autowired
    private StateMachineTaskService stateMachineTaskService;
    @Autowired
    private StateMachineLogService stateMachineLogService;

    @Override
    public void afterStateChange(State<S, E> target, Message<E> message, Transition<S, E> transition,
                                 StateMachine<S, E> stateMachine) {
        log.info("状态改变持久化到数据库");
        MessageHeaders headers = message.getHeaders();
        StateMachineTask task = (StateMachineTask)headers.getHeaders().get(StateMachineConstant.TASK_HEADER);
        String tid = task.getId() + "-" + System.currentTimeMillis();
        log.info("状态发生改变 tid->{}", tid);
        headers.addHeader(TRANSITION_UNIQUE_ID, tid);
        //上下文
        String context = getContext(headers);
        //保存转换日志
        String response = getResponse(task.getResponseData());
        saveLog(task.getMachineCode(), message.getPayload().toString(), transition.getSource().getId().toString(),
            target.getId().toString(), Transition.SUCCESS, response, context);
        //修改数据库
        StateMachineTask update = new StateMachineTask();
        update.setId(task.getId());
        update.setTransactionId(task.getTransactionId());
        update.setMachineState(target.getId().toString());
        update.setRequestData(task.getRequestData());
        update.setResponseData(response);
        update.setMachineContext(context);
        stateMachineTaskService.updateByPrimaryKeySelective(update);
    }

    @Override
    public Exception stateMachineError(StateMachine<S, E> stateMachine, Message<E> eventMsg, Exception e) {
        Transition<S, E> transition = stateMachine.transition();
        //保存转换日志
        StateMachineTask task =
            (StateMachineTask)stateMachine.getEvent().getHeaders().getHeaders().get(StateMachineConstant.TASK_HEADER);
        String errorMsg = ExceptionUtils.getStackTrace(e);
        String context = getContext(eventMsg.getHeaders());
        saveLog(task.getMachineCode(), transition.getEvent().toString(), stateMachine.getState().getId().toString(),
            transition.getTarget().getId().toString(), Transition.FAILED, errorMsg, context);
        //修改数据库
        StateMachineTask update = new StateMachineTask();
        update.setTransactionId(task.getTransactionId());
        update.setId(task.getId());
        update.setMachineContext(context);
        update.setResponseData(errorMsg);
        stateMachineTaskService.updateByPrimaryKeySelective(update);
        return e;
    }

    private void saveLog(String code, String event, String source, String target, String result, String response,
        String context) {
        StateMachineTask original = stateMachineTaskService.findByCode(code);
        //保存log
        StateMachineLog record = new StateMachineLog();
        record.setMachineCode(code);
        record.setEvent(event);
        record.setSource(source);
        record.setTarget(target);
        record.setTransitionResult(result);
        record.setRequest(original.getRequestData());
        record.setResponse(response);
        record.setMachineContext(context);
        stateMachineLogService.insertSelective(record);
    }

    private String getContext(MessageHeaders message) {
        String context = JSON.toJSONString(message, (PropertyFilter)(o, key, value) -> !StateMachineConstant.TASK_HEADER.equals(key));
        context = context.length() > MID_TEXT_LENGTH ? context.substring(0, MID_TEXT_LENGTH) : context;
        return context;
    }

    private String getResponse(String response) {
        return response==null?"":response.length() > MID_TEXT_LENGTH ? response.substring(0, MID_TEXT_LENGTH) : response;
    }

}
