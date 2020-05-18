package org.springclouddev.drools.configure;

import org.springclouddev.drools.jms.DroolsClearListener;
import org.springclouddev.drools.jms.DroolsReceiveListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 增加redis 的 topic listener
 * @author zhaobo
 */
@Component
    public class RedisConfiguration implements ApplicationRunner {
   @Autowired
    DroolsClearListener droolsClearListener;
   @Autowired
    DroolsReceiveListener droolsReceiveListener;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 删除已有的规则
        droolsClearListener.addClearListener();
        // 增加新的规则
        droolsReceiveListener.addClearListener();
    }

}
