package com.gitee.sop.servercommon.configuration;

import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.param.SopHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * 具备支付宝开放平台服务提供能力
 * @author tanghc
 */
public class PabServiceConfiguration extends BaseServiceConfiguration {

    static {
        // 默认版本号为1.0
        ServiceConfig.getInstance().setDefaultVersion("1.0");
    }

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    protected void doAfter() {
        super.doAfter();
        SopHandlerMethodArgumentResolver sopHandlerMethodArgumentResolver = ServiceConfig.getInstance().getMethodArgumentResolver();
        sopHandlerMethodArgumentResolver.setRequestMappingHandlerAdapter(requestMappingHandlerAdapter);
    }
}
