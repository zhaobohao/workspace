package com.gitee.sop.gatewaycommon.route;

/**
 * 转发选择
 * @author tanghc
 */
public interface ForwardChooser<T> {

    /**
     * 返回转发信息
     * @param t 上下文
     * @return 返回转发信息
     */
    ForwardInfo getForwardInfo(T t);

}
