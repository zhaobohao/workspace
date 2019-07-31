package com.gitee.easyopen;

import java.lang.reflect.Method;


/**
 * api信息
 * @author tanghc
 *
 */
public interface ApiMeta {
    /**
     * 获取ApiServce对象
     * @return 返回ApiService对象，即业务对象
     */
    Object getHandler();

    /**
     * 获取接口对应的方法
     * @return 返回方法
     */
    Method getMethod();

    /**
     * 获取方法参数类型
     * @return 返回方法参数类型
     */
    Class<?> getMethodArguClass();

    /**
     * 获取接口名
     * @return 返回接口名
     */
    String getName();

    /**
     * 获取接口版本号
     * @return 返回版本号
     */
    String getVersion();

    /**
     * 是否忽略签名
     * @return true，是
     */
    boolean isIgnoreSign();

    /**
     * 是否忽略验证
     * @return true，是
     */
    boolean isIgnoreValidate();

    /**
     * 是否对返回结果进行包装
     * @return true，是
     */
    boolean isWrapResult();

    /**
     * 是否返回结果到客户端，返回true，表示不输出结果到客户端。可用在导出功能上
     * @see com.gitee.easyopen.annotation.Api#noReturn()
     * @return 返回true，表示不输出结果到客户端
     */
    boolean noReturn();

    /**
     * 是否忽略jwt认证
     * @return 返回true，忽略认证
     */
    boolean isIgnoreJWT();

    /**
     * @return 是否忽略token，true忽略。<br/>
     * 注意：框架本身不会校验token，需要自己实现，可用拦截器实现，这个属性主要配合拦截器使用。
     */
    boolean isIgnoreToken();
}
