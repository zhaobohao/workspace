package org.springexample.statemachine.workflow.action;

import org.springexample.statemachine.core.action.Action;
import org.springexample.statemachine.core.context.MessageHeaders;
import org.springexample.statemachine.core.context.StateContext;
import org.springexample.statemachine.core.exception.StateMachineRetryException;
import org.springexample.statemachine.workflow.model.StateMachineTask;
import org.springexample.statemachine.workflow.model.StateMachineConstant;

import java.util.function.Consumer;

/**
 * 抽象Action，提供方法，方便Action实现类操作Task
 * Created by zhaobo on 2016/4/3
 * @author zhaobo
 */
public interface WorkFlowAction<S, E> extends Action<S, E> {
    /**
     * 从上下文中拿到StateMachineTask对象
     * @param context 上下文
     * @return task
     */
    default StateMachineTask getStateMachineTask(StateContext<S, E> context) {
        MessageHeaders headers = context.getMessage().getHeaders();
        return (StateMachineTask) headers.getHeader(StateMachineConstant.TASK_HEADER);
    }

    /**
     * 异常处理Action, action抛出异常后会进入errorAction，详细请看Actions.errorCallingAction实现
     * @param consumer 业务逻辑
     * @return errorAction
     */
    default Action<S, E> errorAction(Consumer<StateContext<S, E>> consumer) {
        return (s) -> {
            StateMachineTask task = getStateMachineTask(s);
            //如果是最后一次重试
            Exception e = s.getException();
            if (e == null) {
                throw new StateMachineRetryException("未知异常");
            }
            boolean isRetryException = e instanceof StateMachineRetryException;
            //非重试异常
            if (!isRetryException) {
                consumer.accept(s);
            } else {
                //重试异常且最后一次尝试
                if (task.isLastRetry()) {
                    consumer.accept(s);
                } else {
                    throw (StateMachineRetryException) e;
                }
            }
        };
    }
}
