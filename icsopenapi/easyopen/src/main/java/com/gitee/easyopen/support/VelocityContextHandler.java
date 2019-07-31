package com.gitee.easyopen.support;

import org.apache.velocity.VelocityContext;

/**
 * @author tanghc
 */
public interface VelocityContextHandler {
    /**
     * 对doc模板做额外处理
     * @param context
     */
    void processDocVelocityContext(VelocityContext context);

    /**
     * 对限流模板做额外处理
     *
     * @param context
     */
    void processLimitVelocityContext(VelocityContext context);

    /**
     * 对监控模板做额外处理
     *
     * @param context
     */
    void processMonitorVelocityContext(VelocityContext context);

    /**
     * 文档页顶部说明，支持html标签
     * @return 返回文档备注
     */
    String getDocRemark();
}
