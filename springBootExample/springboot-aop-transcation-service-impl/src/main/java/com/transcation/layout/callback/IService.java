package com.transcation.layout.callback;

/**
 * 每个最小执行单元需要实现该接口
 * @author zhaobo wrote on 2017-11-19.
 */
public interface IService<T, V> {
    /**
     * 在这里做耗时操作，如rpc请求、IO等
     *
     * @param object
     *         object
     */
    V action(T object);

    /**
     * 超时、异常时，返回的默认值
     * @return 默认值
     */
    V defaultValue();
}
