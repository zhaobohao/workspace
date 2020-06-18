package org.springexample.statemachine.parser;

import org.springexample.statemachine.core.configurer.StateMachineConfigurer;

/**
 * 状态机解析器
 * Created by zhaobo on 2016/4/10
 * @author zhaobo
 */
public interface StateMachineParser<T> {

    /**
     * 解析器
     * @param config
     * @return
     */
    StateMachineConfigurer parser(T config);
}
