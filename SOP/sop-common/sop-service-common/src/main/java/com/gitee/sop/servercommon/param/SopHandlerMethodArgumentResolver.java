package com.gitee.sop.servercommon.param;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author tanghc
 */
public interface SopHandlerMethodArgumentResolver extends HandlerMethodArgumentResolver {

    /**
     * 设置requestMappingHandlerAdapter
     *
     * @param requestMappingHandlerAdapter requestMappingHandlerAdapter
     */
    void setRequestMappingHandlerAdapter(RequestMappingHandlerAdapter requestMappingHandlerAdapter);
}
