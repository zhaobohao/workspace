package com.gitee.easyopen.bean;

/**
 * 调用方法
 * @author tanghc
 */
public interface MethodCaller {
    /**
     * 执行service类的方法
     * @param invocation 调用信息
     * @return 方法返回对象
     * @throws Exception
     */
    Object call(Invocation invocation) throws Exception;
}
