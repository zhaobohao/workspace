package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.BeanInitializer;

/**
 * @author tanghc
 */
public interface IPBlacklistManager extends BeanInitializer {

    /**
     * 添加IP
     *
     * @param ip ip
     */
    void add(String ip);

    /**
     * 移除黑名单IP
     *
     * @param ip ip
     */
    void remove(String ip);

    /**
     * ip是否在黑名单中
     *
     * @param ip ip
     * @return true：在黑名单中
     */
    boolean contains(String ip);

}
