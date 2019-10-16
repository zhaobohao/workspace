package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.story.api.domain.Story;
import com.gitee.sop.storyweb.controller.result.StoryResult;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付宝服务端，假设签名验证通过后，到达这里进行具体的业务处理。
 * 这里演示如何接受业务参数。
 * @author tanghc
 */
@RestController
public class PermissionDemoController {

    @ApiMapping(value = "permission.story.get", permission = true)
    public StoryResult getStory() {
        StoryResult story = new StoryResult();
        story.setId(1L);
        story.setName("海底小纵队(permission.story.get)");
        return story;
    }

}
