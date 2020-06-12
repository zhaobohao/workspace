package com.transcation.layout.callback;

import com.transcation.service.base.BaseServiceContext;
import com.transcation.service.enums.ServiceStatus;

/**
 * 每个最小执行单元需要实现该接口
 * @author zhaobo wrote on 2017-11-19.
 */
public interface IService<T extends  BaseServiceContext> {
    /**
     * 超时、异常时，返回的默认值
     * @return 默认值
     */
    ServiceStatus defaultValue();
    /**
     *  正向交易
     * @return
     */
    ServiceStatus trade(T context);

    /**
     * 冲正交易
     * @return
     */
    ServiceStatus refund(T context);

    /**
     * 查证流程
     * @return
     */
    ServiceStatus  check(T context);
}
