package com.gitee.sop.servercommon.manager;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author tanghc
 */
public interface RequestMappingEvent {
    /**
     * 注册成功后回调
     * @param apiMappingHandlerMapping
     */
    void onRegisterSuccess(RequestMappingHandlerMapping apiMappingHandlerMapping);
}
