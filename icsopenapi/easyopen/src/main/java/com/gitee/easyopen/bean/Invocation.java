package com.gitee.easyopen.bean;

import com.gitee.easyopen.ApiMeta;

/**
 * @author tanghc
 */
public interface Invocation {

    /**
     * 获取接口信息
     * @return 返回接口信息
     */
    ApiMeta getApiMeta();

    /**
     * 获取方法参数
     * @return 返回方法参数
     */
    Object getArgu();

    /**
     * 执行方法调用
     * @return 返回方法返回内容
     * @throws Exception
     */
    Object process() throws Exception;
}
