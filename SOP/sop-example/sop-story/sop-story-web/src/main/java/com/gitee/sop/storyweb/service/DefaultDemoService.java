package com.gitee.sop.storyweb.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.gitee.sop.story.api.param.DemoParam;
import com.gitee.sop.story.api.result.DemoResult;
import com.gitee.sop.story.api.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Service(version = "1.0.0")
@Slf4j
public class DefaultDemoService implements DemoService {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }

    @Override
    public DemoResult getStory(DemoParam param) {
        log.info("dubbo provider, param: {}", param);
        DemoResult demoResult = new DemoResult();
        demoResult.setId(param.getId());
        demoResult.setName("dubbo 白雪公主, param=" + param);
        return demoResult;
    }
}