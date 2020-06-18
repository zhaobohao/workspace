package org.springexample.statemachine.core.transition;


import org.springexample.statemachine.core.action.Action;
import org.springexample.statemachine.core.context.StateContext;
import org.springexample.statemachine.core.guard.Guard;
import org.springexample.statemachine.core.state.State;

import java.util.Collection;

/**
 * 转换器接口
 * Created by zhaobo on 2016/1/21
 * @param <S> 状态
 * @param <E> 事件
 */
public interface Transition<S, E> {

    /**
     * 是否转换
     * @param context
     * @return
     */
    boolean transit(StateContext<S, E> context);

    void executeTransitionActions(StateContext<S, E> context);

    Collection<Action<S, E>> getActions();

    State<S, E> getSource();

    State<S, E> getTarget();

    Guard<S, E> guard();

    E getEvent();

    String SUCCESS = "SUCCESS";
    String FAILED = "FAILED";

}
