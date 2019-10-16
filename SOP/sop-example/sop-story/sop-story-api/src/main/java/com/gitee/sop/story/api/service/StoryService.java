package com.gitee.sop.story.api.service;

import com.gitee.sop.story.api.domain.Story;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tanghc
 */
@RequestMapping("/story")
public interface StoryService {
    @RequestMapping("/getStory")
    Story getStory(@RequestParam("id")/* 必须指定@RequestParam，且value不能少 */
                           int id);
}
