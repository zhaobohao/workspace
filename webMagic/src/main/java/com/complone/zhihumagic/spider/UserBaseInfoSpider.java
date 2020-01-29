package com.complone.zhihumagic.spider;


import com.complone.zhihumagic.pipeline.UserDetailInfoPipeline;
import com.complone.zhihumagic.task.SavingTask;
import com.complone.zhihumagic.model.UserDetailInfo;
import com.complone.zhihumagic.pipeline.UserBaseInfoPipeline;
import com.complone.zhihumagic.processor.UserBaseInfoProcessor;
import com.complone.zhihumagic.service.UserBaseInfoService;
import com.complone.zhihumagic.task.StartTask;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.management.JMException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class UserBaseInfoSpider{

    Logger logger = LoggerFactory.getLogger(UserBaseInfoSpider.class);

    @Autowired
    private StartTask startTask;

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    @Autowired
    private UserBaseInfoProcessor userBaseInfoProcessor;

    private static final String START_URL = "https://www.zhihu.com/people/excited-vczh";

    public UserBaseInfoSpider(){

    }

    private Site site = Site
            .me()
            .setCycleRetryTimes(5)
            .setRetryTimes(5)
            .setSleepTime(1000)
            .setTimeOut(3 * 60 * 1000)
            .setUserAgent(
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
            .setHttpProxy(new HttpHost("45.32.50.126", 4399))
            .setCharset("UTF-8");

    public void crawl(){
        BlockingDeque<UserDetailInfo> blockingDeque=new LinkedBlockingDeque<UserDetailInfo>();
        ExecutorService executorService= Executors.newFixedThreadPool(4);
        SavingTask savingTask = new SavingTask(blockingDeque);
        savingTask.setUserBaseInfoService(userBaseInfoService);
        Spider spider = Spider.create(new UserBaseInfoProcessor()).addUrl(START_URL)
                .scheduler(new QueueScheduler())
                .addPipeline(new UserBaseInfoPipeline(blockingDeque))
                .setExecutorService(executorService)
                .thread(1);

//        executorService.execute(savingTask);
        StartTask startTask = new StartTask();
        startTask.init(executorService);
        startTask.execute(savingTask);
        spider.run();



    }

    public static void main(String[] args){
        Spider spider = Spider.create(new UserBaseInfoProcessor())
                .addUrl(START_URL)
                .addPipeline(new UserDetailInfoPipeline())
                .thread(1);
        spider.run();
    }
}
