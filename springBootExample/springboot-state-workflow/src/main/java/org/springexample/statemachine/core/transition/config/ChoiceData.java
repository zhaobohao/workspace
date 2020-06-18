package org.springexample.statemachine.core.transition.config;

import org.springexample.statemachine.core.guard.Guard;
import lombok.Getter;
import lombok.Setter;

/**
 * 选择转换器类
 * Created by zhaobo on 2016/1/21
 * @param <S> 状态
 * @param <E> 事件
 */
@Getter
@Setter
public class ChoiceData<S, E> {
    private final S source;
    private final S target;
    private final Guard<S, E> guard;

    public ChoiceData(S source, S target, Guard<S, E> guard) {
        this.source = source;
        this.target = target;
        this.guard = guard;
    }
}
