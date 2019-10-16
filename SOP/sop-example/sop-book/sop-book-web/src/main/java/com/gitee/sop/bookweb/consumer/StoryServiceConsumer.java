package com.gitee.sop.bookweb.consumer;

import com.gitee.sop.story.api.service.StoryService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 调用story服务
 *
 * @author tanghc
 */
// value对应的spring.application.name
@FeignClient("story-service")
public interface StoryServiceConsumer extends StoryService {
}
