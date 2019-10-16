package com.gitee.sop.gatewaycommon.manager;

import java.util.Map;

/**
 * 参数格式化
 *
 * @author tanghc
 */
public interface Formatter<T extends Map<String, Object>> {

    /**
     * 参数格式化，即动态修改请求参数
     *
     * @param requestParams 原始请求参数，在此基础上追加或修改参数
     */
    void format(T requestParams);
}
