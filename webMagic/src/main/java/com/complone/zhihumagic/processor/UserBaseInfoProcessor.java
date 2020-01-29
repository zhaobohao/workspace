package com.complone.zhihumagic.processor;

import com.complone.zhihumagic.pipeline.UserDetailInfoPipeline;
import com.complone.zhihumagic.task.SavingTask;
import com.complone.zhihumagic.model.UserDetailInfo;
import com.complone.zhihumagic.pipeline.UserBaseInfoPipeline;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Component("userBaseInfoProcessor")
public class UserBaseInfoProcessor implements PageProcessor {


    private static final String START_URL  = "https://www.zhihu.com/people/excited-vczh/activities";

    private static final String TARGET_USER_BASE_INFO = "https://www\\.zhihu\\.com/people/[\\w-]+";

    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(300).setTimeOut(3 * 60 * 1000)
            .setUserAgent(
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
            .setCharset("UTF-8")
            .addCookie("_xsrf", "f3jahdGruGNjLacAKaxX3SAjXJPtXWmn")
            .addCookie("_zap", "a1139d27-1bd3-4631-ab8d-8c939a7ef471")
            .addCookie("z_c0", "2|1:0|10:1540302879|4:z_c0|92:Mi4xM2ZhQkFnQUFBQUFBd0dBdzVQQ01EU1lBQUFCZ0FsVk5IM2E4WEFCMDNqbXhqaVJYSW9aaFpYd2g5eHo3WC12aXBR|09b5c10ce3f5cae4e4ab532937e4efc31d9773025214e84c29775d4930a13a2d")
            .addCookie("__utmc", "155987696.1522404687.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)") ;


    public void process(Page page) {
        //进入详细页进行进行抓取
        List<String> urls = page.getHtml().links().regex(TARGET_USER_BASE_INFO).all();
        UserDetailInfo userDetailInfo = new UserDetailInfo();
        for(String s:urls){
            if (StringUtils.isBlank(s) || s.equals("#") || s.startsWith("javascript:")) {
                continue;
            }
            //page.addTargetRequest(s+"/about");
//            System.err.println(s);
        }


//        if (page.getResultItems().get("PageUrl") == null) {
//            //skip this page
//            page.setSkip(true);
//
//        }
//        System.out.println(page.getHtml());

        userDetailInfo.setPageurl(page.getHtml().xpath("//div[@itemprop='people']/meta[@itemprop='url']/@content").toString());
        userDetailInfo.setNickname(page.getHtml().xpath("//div[@class='ProfileHeader-contentHead']/h1/span[1]/text()").toString());
        userDetailInfo.setBusiness(page.getHtml().xpath("//div[@class='ProfileHeader-contentBody']/div/div/div[2]/text()").toString());
        userDetailInfo.setEmployment(page.getHtml().xpath("//div[@class='ProfileHeader-contentBody']/div/div/div[1]/text()").toString());
        userDetailInfo.setPosition(page.getHtml().xpath("//div[@class='ProfileHeader-contentBody']/div/div[@class='ProfileHeader-detail']").toString());
        userDetailInfo.setCollecters(1);
        //Integer.valueOf(page.getHtml().xpath("//div[@class='zm-profile-module-desc']/span[4]/strong/text()").toString())
        //System.err.println(page.getHtml().xpath("//div[@class='zm-profile-module-desc']/span[4]/strong/text()").toString());
        //System.err.println(page.getHtml().xpath("//div[@class='zm-profile-module-desc']/span[5]/strong/text()").toString());

//        userDetailInfo.setShares(Integer.valueOf(page.getHtml().xpath("//div[@class='ProfileHeader-contentHead']/h1/a/span[2]/text()").toString()));
//        userDetailInfo.setEducation(page.getHtml().xpath("//div[@class='ProfileHeader-detail']/div[4]/div/div/text()").toString());
//        userDetailInfo.setEducationextra(page.getHtml().xpath("//div[@class='ProfileHeader-detail']/div[4]/div/div/text()").toString());
        userDetailInfo.setAddtime(new Date());

        //状态
        String status = page.getHtml().xpath("//div[@class='Profile-sideColumn']/div[@class='Card']/div[2]/div[2]/div[2]/text()").toString();
        if(StringUtils.isEmpty(status))
            userDetailInfo.setStatus("active");
        else
            userDetailInfo.setStatus("noActive");

        //性别
        String gender = page.getHtml().xpath("//div[@class='Card FollowshipCard']/div/a[2]/div/strong/text()").toString();
        if(StringUtils.isEmpty(gender))
            userDetailInfo.setGender("unknow");
        else
            userDetailInfo.setGender(gender);

        page.putField("userDetailInfo",userDetailInfo);

        page.addTargetRequests(page.getHtml().links().regex(TARGET_USER_BASE_INFO).all());

    }

    public void start(PageProcessor pageProcessor,Pipeline pipeline,ExecutorService executorService,SavingTask savingTask) {
        Spider spider = Spider.create(pageProcessor).addUrl(START_URL)
                .addPipeline(pipeline)
                .setExecutorService(executorService)
                .thread(1);
        executorService.execute(savingTask);
        spider.run();

    }


    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//
//        BlockingDeque<UserDetailInfo> blockingDeque=new LinkedBlockingDeque<UserDetailInfo>();
//        ExecutorService executorService= Executors.newFixedThreadPool(4);
//        UserBaseInfoPipeline userBaseInfoPipeline = new UserBaseInfoPipeline(blockingDeque);
//        SavingTask savingTask=new SavingTask(blockingDeque);
//
//        Spider spider = Spider.create(new UserBaseInfoProcessor()).addUrl(START_URL)
//                .scheduler(new QueueScheduler())
//                .addPipeline(new UserBaseInfoPipeline(blockingDeque))
//                .thread(1);
//        executorService.execute(savingTask);
//        spider.run();
        Spider spider = Spider.create(new UserBaseInfoProcessor())
                .addUrl(START_URL)
                .addPipeline(new UserDetailInfoPipeline())
                .thread(1);
        spider.run();
    }

}
