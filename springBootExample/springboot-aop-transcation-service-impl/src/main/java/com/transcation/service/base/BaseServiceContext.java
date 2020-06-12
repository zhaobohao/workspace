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
}
