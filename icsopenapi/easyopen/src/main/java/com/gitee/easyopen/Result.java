package com.gitee.easyopen;

import java.io.Serializable;

/**
 * 返回结果，后续自定义的返回类需要实现这个接口。
 * @author tanghc
 */
public interface Result extends Serializable{
    /**
     * 设置状态码
     * @param code 状态码
     */
    void setCode(Object code);

    /**
     * 设置消息
     * @param msg 消息
     */
    void setMsg(String msg);

    /**
     * 设置数据内容
     * @param data 数据内容
     */
    void setData(Object data);
}
