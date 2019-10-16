package com.gitee.sop.gatewaycommon.secret;

import com.gitee.sop.gatewaycommon.bean.BeanInitializer;
import com.gitee.sop.gatewaycommon.bean.Isv;

/**
 * Isv管理
 *
 * @author tanghc
 */
public interface IsvManager<T extends Isv> extends BeanInitializer {

    /**
     * 更新isv
     *
     * @param t isv
     */
    void update(T t);

    /**
     * 删除isv
     *
     * @param appKey isv对应的appKey
     */
    void remove(String appKey);

    /**
     * 获取isv
     *
     * @param appKey isv对应的key
     * @return 返回isv
     */
    T getIsv(String appKey);

}
