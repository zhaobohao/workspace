package com.complone.zhihumagic.controller;

import com.alibaba.fastjson.JSON;
import com.complone.zhihumagic.pipeline.UserDetailInfoPipeline;
import com.complone.zhihumagic.processor.UserDetailProcessor;
import com.complone.zhihumagic.service.UserDetailInfoService;
import com.complone.zhihumagic.task.SavingTask;
import com.complone.zhihumagic.mapper.GithubUserInfoMapper;
import com.complone.zhihumagic.mapper.UserDetailInfoMapper;
import com.complone.zhihumagic.model.GithubUserInfo;
import com.complone.zhihumagic.model.UserBaseInfo;
import com.complone.zhihumagic.model.UserDetailInfo;
import com.complone.zhihumagic.pipeline.GithubUserPipeline;
import com.complone.zhihumagic.pipeline.UserBaseInfoPipeline;
import com.complone.zhihumagic.processor.GitHubProcessor;
import com.complone.zhihumagic.processor.UserBaseInfoProcessor;
import com.complone.zhihumagic.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
@EnableAsync
@Controller
public class BaseController {
    @Autowired
    private UserBaseInfoService userBaseInfoService;

    @Autowired
    private UserDetailInfoMapper userDetailInfoMapper;

    @Autowired
    private GithubUserInfoMapper githubUserInfoMapper;

    @RequestMapping(value = "/searchByName")
    public  @ResponseBody
    List<UserDetailInfo> searchByName(@RequestParam(value = "name",  required = true)String name){
        Example example1 = new Example(UserBaseInfo.class);
        example1.selectProperties("nickname","location","weiboUrl","headline","description");
        example1.createCriteria().andLike("nickname", name);
        List<UserDetailInfo> result = (List<UserDetailInfo>) userDetailInfoMapper.selectByExample(example1);
        System.out.println("查找昵称为"+name+"结果为 "+JSON.toJSONString(result));
        return result;
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public @ResponseBody void test(){
        UserDetailInfo ui = new UserDetailInfo();
        ui.setPageurl("https://ww.eo4dsddw3.com");
        ui.setNickname("v2ex");
        ui.setStatus("获得 256,661 次感谢 ， 355,734 次收藏");
        ui.setGender("768,840");
        ui.setAddtime(new Date());
        userBaseInfoService.insertOne(ui);
    }

    @RequestMapping(value = "/desc",method = RequestMethod.POST)
    public @ResponseBody int testGIthub(){
        GithubUserInfo githubUserInfo = new GithubUserInfo();
        githubUserInfo.setNickname("nacy");
        githubUserInfo.setAuthor("complone");
        int row = githubUserInfoMapper.insertGithubUserInfo(githubUserInfo);
        return row;
    }

    @Autowired
    private GitHubProcessor gitHubProcessor;

    @Autowired
    private GithubUserPipeline githubUserPipeline;

    @RequestMapping("/start")
    public String start() {
        gitHubProcessor.start(gitHubProcessor,githubUserPipeline);
        return "GithubSpider is close!";
    }

    @Autowired
    private UserBaseInfoProcessor userBaseInfoProcessor;
    BlockingDeque<UserDetailInfo> blockingDeque=new LinkedBlockingDeque<UserDetailInfo>();
    UserBaseInfoPipeline userBaseInfoPipeline = new UserBaseInfoPipeline(blockingDeque);
    ExecutorService executorService= Executors.newFixedThreadPool(4);
    SavingTask savingTask = new SavingTask(blockingDeque);

    @RequestMapping("/begin")
    public String begin(){

        userBaseInfoProcessor.start(userBaseInfoProcessor,userBaseInfoPipeline,executorService,savingTask);
        return "Crawl is begin";
    }

    @Autowired
    private UserDetailProcessor userDetailProcessor;
    @Autowired
    private UserDetailInfoPipeline userDetailInfoPipeline;

    @RequestMapping("/again")
    public String agAin(){
        userDetailProcessor.start(userDetailProcessor,userDetailInfoPipeline);
        return "Crawl is again!";
    }
}
