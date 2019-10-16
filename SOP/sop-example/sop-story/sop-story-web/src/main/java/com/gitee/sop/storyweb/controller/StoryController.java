package com.gitee.sop.storyweb.controller;

import com.gitee.sop.story.api.domain.Story;
import com.gitee.sop.story.api.service.StoryService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanghc
 */
@RestController
public class StoryController implements StoryService {

    // 提供给Feign的服务
    @Override
    public Story getStory(int id) {
        Story story = new Story();
        story.setId(id);
        story.setName("海底小纵队(Feign)");
        return story;
    }


}
