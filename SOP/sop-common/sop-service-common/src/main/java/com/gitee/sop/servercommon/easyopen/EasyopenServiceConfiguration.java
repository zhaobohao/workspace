package com.gitee.sop.servercommon.easyopen;

import com.gitee.easyopen.ApiContext;
import com.gitee.sop.servercommon.configuration.BaseServiceConfiguration;
import com.gitee.sop.servercommon.manager.ServiceRouteController;
import org.springframework.context.annotation.Bean;

/**
 * 提供给easyopen项目使用
 *
 * @author tanghc
 */
public class EasyopenServiceConfiguration extends BaseServiceConfiguration {

    static {
        ApiContext.getApiConfig().setIgnoreValidate(true);
    }

    @Bean
    ServiceRouteController serviceRouteController() {
        return new EasyopenServiceRouteController();
    }

}
