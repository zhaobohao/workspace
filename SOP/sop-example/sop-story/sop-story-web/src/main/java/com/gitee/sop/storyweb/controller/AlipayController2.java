package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.annotation.ApiAbility;
import com.gitee.sop.storyweb.controller.param.StoryParam;
import com.gitee.sop.storyweb.controller.result.StoryResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付宝服务端，假设签名验证通过后，到达这里进行具体的业务处理。
 *
 * @author tanghc
 */
@RestController
@Slf4j
// 注解放在这里，表示类中的方法都具备接口开放能力
@ApiAbility
public class AlipayController2 {

    // http://localhost:2222/getStory44
    // 遗留接口具备开放平台能力
    @PostMapping("/getStory44")
    public StoryResult getStory22(@RequestBody StoryParam param) {
        StoryResult story = new StoryResult();
        story.setId(1L);
        story.setName("遗留接口44,param:" + param);
        return story;
    }
}
