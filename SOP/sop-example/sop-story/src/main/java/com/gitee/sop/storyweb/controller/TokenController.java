package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.servercommon.bean.OpenContext;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.storyweb.controller.param.StoryParam;
import com.gitee.sop.storyweb.controller.result.StoryResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanghc
 */
@RestController
@Slf4j
@Api(tags = "故事接口")
public class TokenController {

    @ApiMapping(value = "story.token.get", needToken = true/* 设置true，网关会校验token是否存在 */)
    public StoryResult token(StoryParam story) {
        OpenContext openContext = ServiceContext.getCurrentContext().getOpenContext();
        String appAuthToken = openContext.getAppAuthToken();
        StoryResult result = new StoryResult();
        result.setName("appAuthToken:" + appAuthToken);
        return result;
    }
}
