package org.springexample.statemachine.core.configurer;

import org.springexample.statemachine.core.interceptor.StateMachineInterceptorConfigurer;
import org.springexample.statemachine.core.state.config.StateConfigurer;
import org.springexample.statemachine.core.transition.config.TransitionConfigurer;

/**
 * 状态机配置
 * Created by zhaobo on 2016-01-21
 * @author zhaobo
 * @param <S> 状态
 * @param <E> 事件
 */
public interface StateMachineConfigurer<S, E> {
    /**
     * 状态机名称
     */
    String getName();
    /**
     * 状态配置
     * @param states 状态
     * @throws Exception
     */
    void configure(StateConfigurer<S, E> states);

    /**
     * 转换配置
     * @param transitions 转换
     * @throws Exception
     */
    void configure(TransitionConfigurer<S, E> transitions);

    /**
     * 配置拦截器
     * @param interceptors 拦截器
     */
    void configure(StateMachineInterceptorConfigurer<S, E> interceptors);
}
