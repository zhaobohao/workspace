package com.gitee.easyopen;

import com.gitee.easyopen.bean.ApiDefinition;

/**
 * 接口注册事件
 * @author tanghc
 */
public interface ApiRegistEvent {
    /**
     * 某个接口注册成功后触发
     * @param apiDefinition 接口信息
     */
    void onSuccess(ApiDefinition apiDefinition);
}
