package com.gitee.sop.servercommon.bean;

/**
 * @author tanghc
 */
public interface OpenBeanFactory {

    /**
     * 返回业务参数对象
     *
     * @param clazz 业务参数类class
     * @param <T>   业务参数对象
     * @return 返回业务参数对象
     */
    <T> T getBizObject(Class<T> clazz);
}
