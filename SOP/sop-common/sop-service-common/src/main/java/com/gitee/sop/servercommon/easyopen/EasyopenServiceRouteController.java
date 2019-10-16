package com.gitee.sop.servercommon.easyopen;

import com.gitee.sop.servercommon.manager.ApiMetaBuilder;
import com.gitee.sop.servercommon.manager.ServiceRouteController;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanghc
 */
@RestController
public class EasyopenServiceRouteController extends ServiceRouteController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    protected ApiMetaBuilder getApiMetaBuilder() {
        return new EasyopenApiMetaBuilder(applicationContext, getEnvironment());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}