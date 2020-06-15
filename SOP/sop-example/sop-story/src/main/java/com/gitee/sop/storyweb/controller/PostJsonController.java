package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.servercommon.bean.OpenContext;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.storyweb.controller.param.StoryParam;
import com.gitee.sop.storyweb.controller.result.StoryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author tanghc
 */
@RestController
public class PostJsonController {

    /**
     * 演示客户端使用json方式请求（application/json）
     * @param param
     * @return
     */
    @ApiMapping("demo.post.json")
    public StoryResult postJson(@RequestBody StoryParam param) {
        // 获取开放平台请求参数
        OpenContext openContext = ServiceContext.getCurrentContext().getOpenContext();
        List<Object> params = Arrays.asList(
          openContext.getAppId(),
          openContext.getMethod(),
          openContext.getVersion()
        );
        StoryResult result = new StoryResult();
        result.setId(1L);
        result.setName("参数：" + param.getName() + ", openParams:" + StringUtils.join(params));
        return result;
    }

}
