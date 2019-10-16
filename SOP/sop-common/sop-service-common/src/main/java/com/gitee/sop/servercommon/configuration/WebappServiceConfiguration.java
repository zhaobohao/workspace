package com.gitee.sop.servercommon.configuration;

import com.gitee.sop.servercommon.bean.ServiceConfig;

/**
 * 通用web
 * @author tanghc
 */
public class WebappServiceConfiguration extends BaseServiceConfiguration {
    static {
        // 默认版本号为1.0
        ServiceConfig.getInstance().setDefaultVersion("1.0");
        ServiceConfig.getInstance().setIgnoreValidate(true);
        ServiceConfig.getInstance().setWebappMode(true);
    }
}
