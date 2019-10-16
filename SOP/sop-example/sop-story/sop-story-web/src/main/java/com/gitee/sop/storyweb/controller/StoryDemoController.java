package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.story.api.domain.Story;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanghc
 */
@RestController
public class StoryDemoController {

    @ApiMapping(value = "story.demo.get")
    public Story getStory() {
        Story story = new Story();
        story.setId(1);
        story.setName("白雪公主");
        return story;
    }

}
