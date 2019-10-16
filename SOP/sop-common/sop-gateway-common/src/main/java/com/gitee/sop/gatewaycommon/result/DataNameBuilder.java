package com.gitee.sop.gatewaycommon.result;

/**
 * @author tanghc
 */
public interface DataNameBuilder {
    /**
     * 构建数据节点名称
     * @param method
     * @return
     */
    String build(String method);
}
