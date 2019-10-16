package com.gitee.sop.story.api.service;

import com.gitee.sop.story.api.param.DemoParam;
import com.gitee.sop.story.api.result.DemoResult;

public interface DemoService {

    String sayHello(String name);

    /**
     * 获取故事名称
     * @param param
     * @return
     */
    DemoResult getStory(DemoParam param);

}