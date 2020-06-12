package com.transcation.service.base;

public interface BaseServiceContext {
    /**
     * 返回业务数据jsonString
     * @return
     */
    String getData();

    /**
     * 获取重试的最大次数
     * @return
     */
    int retry();

    /**
     * 当前交易id
     * @return
     */
    String transcationId();

    /**
     *  当前service的id
     * @return
     */
    String  serviceId();
}
