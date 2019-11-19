package com.gitee.sop.storyweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.servercommon.annotation.ApiAbility;
import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.servercommon.bean.OpenContext;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.story.api.domain.Story;
import com.gitee.sop.storyweb.controller.param.CategoryParam;
import com.gitee.sop.storyweb.controller.param.StoryParam;
import com.gitee.sop.storyweb.controller.result.CategoryResult;
import com.gitee.sop.storyweb.controller.result.StoryResult;
import com.gitee.sop.storyweb.controller.result.TreeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 支付宝服务端，假设签名验证通过后，到达这里进行具体的业务处理。
 *
 * @author tanghc
 */
@RestController
@Slf4j
@Api(tags = "故事接口")
public class AlipayController {

    // http://localhost:2222/story_get
    // 原生的接口，可正常调用
    @RequestMapping("story_get")
    public StoryResult story_get() {
        StoryResult result = new StoryResult();
        result.setId(1L);
        result.setName("海底小纵队(原生)");
        return result;
    }

    // http://localhost:2222/story.get/
    // 接口名，使用默认版本号
    @ApiMapping(value = "story.get")
    public StoryResult storyget() {
        // 获取开放平台参数
        OpenContext openContext = ServiceContext.getCurrentContext().getOpenContext();
        String appId = openContext.getAppId();
        StoryResult result = new StoryResult();
        result.setId(1L);
        result.setName("海底小纵队(默认版本号), app_id:" + appId);
        return result;
    }

    // http://localhost:2222/story.get/?version=1.1
    // 接口名 + 版本号
    @ApiMapping(value = "story.get", version = "1.1")
    public StoryResult getStory2() {
        StoryResult result = new StoryResult();
        result.setId(1L);
        result.setName("海底小纵队1.0");
        return result;
    }

    // http://localhost:2222/story.get/?name=111&version=2.0
    // 接口名 + 版本号
    // StoryParam对应biz_content内容
    @ApiMapping(value = "story.get", version = "2.0")
    public StoryResult getStory20(StoryParam param) {
        StoryResult result = new StoryResult();
        BeanUtils.copyProperties(param, result);
        return result;
    }

    // 忽略验证
    @ApiMapping(value = "story.get", version = "2.1", ignoreValidate = true)
    public StoryResult getStory21(StoryParam story) {
        OpenContext openContext = ServiceContext.getCurrentContext().getOpenContext();
        // 此处的param和story参数是一样的
        StoryParam param = openContext.getBizObject(StoryParam.class);
        boolean isSame = story == param;
        StoryResult result = new StoryResult();
        result.setName(story.getName() + ", story.get2.1, ignoreValidate = true, story==param:" + isSame);
        return result;
    }

    /**
     * 另一种方式，OpenContext泛型参数填bizObject类<br>
     * 调用openContext.getBizObject()可直接获得对象<br>
     * 此方式等价于：
     * <pre>
     * public Story getStory22(Story bizObject) {
     *     OpenContext openContext = ServiceContext.getCurrentContext().getOpenContext();
     *     // 获取appid，更多方法查看OpenContext类
     *     String appId = openContext.getAppId();
     *     System.out.println(appId);
     *     return bizObject;
     * }
     * </pre>
     *
     * @param openContext
     * @return
     */
    @ApiMapping(value = "story.get", version = "2.2")
    public StoryResult getStory22(OpenContext<StoryParam> openContext) {
        StoryParam bizObject = openContext.getBizObject();
        // 获取appid，更多方法查看OpenContext类
        String appId = openContext.getAppId();
        StoryResult result = new StoryResult();
        result.setName("appId:" + appId + ", " + bizObject.getName());
        return result;
    }

    @ApiMapping(value = "story.get", version = "2.3")
    public StoryResult getStory23(StoryParam param, HttpServletRequest request) {
        OpenContext openContext = ServiceContext.getCurrentContext().getOpenContext();
        String appId = openContext.getAppId();
        System.out.println(appId);
        StoryResult story = new StoryResult();
        story.setName("appId:" + appId + ", " + param.getName() + "，ip:" + request.getLocalAddr());
        return story;
    }

    // http://localhost:2222/getStory2
    // 遗留接口具备开放平台能力
    @ApiAbility
    @GetMapping("getStory2")
    public StoryResult getStory2_0(@RequestBody JSONObject param) {
        StoryResult story = new StoryResult();
        story.setId(1L);
        story.setName("海底小纵队(默认版本号),param:" + param);
        return story;
    }

    // http://localhost:2222/getStory2?version=2.1
    // 遗留接口具备开放平台能力，在原来的基础上加版本号
    @ApiAbility(version = "2.1")
    @GetMapping("getStory2")
    public StoryResult getStory2_1() {
        StoryResult story = new StoryResult();
        story.setId(1L);
        story.setName("海底小纵队2.1");
        return story;
    }

    @ApiAbility(ignoreValidate = true)
    @GetMapping("/empinfo/get")
    @ApiOperation(value="遗留接口", notes = "遗留接口")
    public StoryResult getEmpInfo() {
        StoryResult story = new StoryResult();
        story.setId(1L);
        story.setName("遗留接口");
        return story;
    }

    // http://localhost:2222/alipay.story.get/
    @ApiOperation(value="获取故事信息2", notes = "获取故事信息2的详细信息")
    @ApiMapping(value = "alipay.story.get")
    public StoryResult getStory(StoryParam param) {
        StoryResult story = new StoryResult();
        story.setId(1L);
        story.setName("海底小纵队(alipay.story.get1.0), param:" + param);
        return story;
    }

    /**
     * @param param 对应biz_content中的内容，并自动JSR-303校验
     * @return
     */
    @ApiMapping(value = "alipay.story.get", version = "1.2")
    public StoryResult getStory11(StoryParam param) {
        StoryResult story2 = new StoryResult();
        story2.setId(1L);
        story2.setName("海底小纵队(alipay.story.get1.2), param:" + param);
        return story2;
    }

    /**
     * 验证字符串乱码问题
     *
     * @param param
     * @return
     */
    @ApiMapping(value = "story.string.get", version = "1.0")
    public String string(StoryParam param) {
        StoryResult story2 = new StoryResult();
        story2.setId(1L);
        story2.setName("海底小纵队");
        return JSON.toJSONString(story2);
    }

    /**
     * 参数绑定
     *
     * @param story 对应biz_content中的内容，并自动JSR-303校验
     * @return
     */
    @ApiOperation(value = "获取故事信息", notes = "说明接口的详细信息，介绍，用途，注意事项等。")
    @ApiMapping(value = "alipay.story.find")
    public StoryResult getStory2(StoryParam story) {
        log.info("获取故事信息参数, story: {}", story);
        // 获取其它参数
        OpenContext openContext = ServiceContext.getCurrentContext().getOpenContext();
        String app_id = openContext.getAppId();
        StoryResult result = new StoryResult();
        result.setName("白雪公主, app_id:" + app_id);
        result.setGmt_create(new Date());
        return result;
    }

    @ApiOperation(value = "返回数组结果", notes = "返回数组结果")
    @ApiMapping(value = "alipay.story.find2")
    public List<StoryResult> getStory3(StoryParam story) {
        int index = 0;
        StoryResult storyVO = new StoryResult();
        storyVO.setId(1L);
        storyVO.setName("白雪公主, index:" + index++);
        storyVO.setGmt_create(new Date());

        StoryResult storyVO2 = new StoryResult();
        storyVO2.setId(1L);
        storyVO2.setName("白雪公主, index:" + index++);
        storyVO2.setGmt_create(new Date());

        return Arrays.asList(storyVO, storyVO2);
    }

    @ApiAbility
    @RequestMapping("getJson")
    public Object getJson(@RequestBody JSONObject param) {
        return param;
    }


    /**
     * 演示文档表格树
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "获取分类信息", notes = "演示表格树")
    @ApiMapping(value = "alipay.category.get", method = RequestMethod.POST)
    public CategoryResult getCategory(CategoryParam param) {
        System.out.println(param);
        StoryResult result = new StoryResult();
        result.setId(1L);
        result.setName("白雪公主");
        result.setGmt_create(new Date());
        CategoryResult categoryResult = new CategoryResult();
        categoryResult.setCategoryName("娱乐");
        categoryResult.setStoryResult(result);
        return categoryResult;
    }

    /**
     * 树状返回
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "树状返回", notes = "树状返回")
    @ApiMapping(value = "alipay.tree.get", method = RequestMethod.POST)
    public TreeResult tree(StoryParam param) {
        return new TreeResult();
    }

    // 测试参数绑定，http://localhost:2222/story/getStory4?biz_content=%7b%22id%22%3a1%2c%22name%22%3a%22aaaa%22%7d
    @ApiAbility
    @GetMapping("getStory4")
    public StoryResult getStory4(Story param, P p2) {
        System.out.println(param + ", p2=" + p2);
        StoryResult result = new StoryResult();
        result.setId(1L);
        result.setName("海底小纵队(默认版本号)" + param + ", p2=" + p2);
        return result;
    }

    @Data
    public static class P {
        private String name;
    }
}
