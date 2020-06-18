package org.springexample.statemachine.core.guard;


import org.springexample.statemachine.core.context.StateContext;

/**
 * 断言接口
 * Created by zhaobo on 2016/1/21
 * @param <S> 状态
 * @param <E> 事件
 */
public interface Guard<S, E> {

    boolean evaluate(StateContext<S, E> context);

}
