package com.gitee.easyopen.server.config;

import com.gitee.easyopen.bean.Api;
import com.gitee.easyopen.doc.AbstractApiDocCreator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tanghc
 */
public class ControllerDocCreator extends AbstractApiDocCreator<Controller, RequestMapping> {
    public ControllerDocCreator(String defaultVersion, ApplicationContext applicationContext) {
        super(defaultVersion, applicationContext);
    }

    @Override
    protected Class<Controller> getServiceAnnotationClass() {
        return Controller.class;
    }

    @Override
    protected Class<RequestMapping> getMethodAnnotationClass() {
        return RequestMapping.class;
    }

    @Override
    protected Api getApi(RequestMapping requestMapping) {
        return new Api("http://localhost:8080/" + requestMapping.value()[0], "");
    }
}
