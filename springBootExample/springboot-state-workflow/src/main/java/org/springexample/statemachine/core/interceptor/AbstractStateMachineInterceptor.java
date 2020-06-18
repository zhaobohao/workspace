package org.springexample.statemachine.core.interceptor;

import org.springexample.statemachine.core.StateMachine;
import org.springexample.statemachine.core.context.Message;
import org.springexample.statemachine.core.state.State;
import org.springexample.statemachine.core.transition.Transition;
import lombok.extern.slf4j.Slf4j;

/**
 * 状态机拦截器 Created by zhaobo on 2016/1/21
 *
 * @param <S> 状态
 * @param <E> 事件
 */
@Slf4j
public class AbstractStateMachineInterceptor<S, E> implements StateMachineInterceptor<S, E> {
    @Override
    public Message<E> preEvent(Message<E> message, StateMachine<S, E> stateMachine) {
        log.info("preEvent In StateMachineInterceptor...");
        return message;
    }

    @Override
    public void preStateChange(State<S, E> state, Message<E> message, Transition<S, E> transition,
        StateMachine<S, E> stateMachine) {
        log.info("preStateChange In StateMachineInterceptor...");
    }

    @Override
    public void afterStateChange(State<S, E> state, Message<E> message, Transition<S, E> transition,
        StateMachine<S, E> stateMachine) {
        log.info("afterStateChange In StateMachineInterceptor...");
    }

    @Override
    public Exception stateMachineError(StateMachine<S, E> stateMachine, Message<E> eventMsg, Exception exception) {
        log.info("stateMachineError In StateMachineInterceptor...");
        return exception;
    }
}
