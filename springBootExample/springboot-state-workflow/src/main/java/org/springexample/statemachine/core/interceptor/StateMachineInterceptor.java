package org.springexample.statemachine.core.interceptor;

import org.springexample.statemachine.core.StateMachine;
import org.springexample.statemachine.core.context.Message;
import org.springexample.statemachine.core.state.State;
import org.springexample.statemachine.core.transition.Transition;

/**
 * 状态机拦截器 Created by zhaobo on 2016/1/21
 *
 * @param <S> 状态
 * @param <E> 事件
 */
public interface StateMachineInterceptor<S, E> {

    Message<E> preEvent(Message<E> message, StateMachine<S, E> stateMachine);

    void preStateChange(State<S, E> state, Message<E> message, Transition<S, E> transition,
                        StateMachine<S, E> stateMachine);

    void afterStateChange(State<S, E> state, Message<E> message, Transition<S, E> transition,
                          StateMachine<S, E> stateMachine);

    Exception stateMachineError(StateMachine<S, E> stateMachine, Message<E> eventMsg, Exception exception);
}
