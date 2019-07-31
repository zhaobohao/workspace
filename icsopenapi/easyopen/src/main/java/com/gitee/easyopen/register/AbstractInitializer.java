package com.gitee.easyopen.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.gitee.easyopen.ApiConfig;

/**
 * 负责启动时的初始化工作
 * 
 * @author tanghc
 *
 */
public abstract class AbstractInitializer implements Initializer, RegistCallback {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onRegistFinished(ApiConfig apiConfig) {
    }

    @Override
    public synchronized void init(ApplicationContext applicationContext, ApiConfig apiConfig) {
        Assert.notNull(applicationContext, "applicationContext不能为null");
        Assert.notNull(apiConfig, "apiConfig不能为null");
        try {
            // 注册接口
            new ApiRegister(apiConfig, applicationContext).regist(this);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.exit(0);
        }
    }

}
