package org.springexample.statemachine.core.builder;

/**
 * 类似StringBuilder <I> 需要拼接的对象
 * Created by zhaobo on 2016-01-21.
 */
public interface ConfigurerBuilder<I> {
    /**
     * 拼接方法
     * @return 需要拼接的类
     */
    I and();
}
