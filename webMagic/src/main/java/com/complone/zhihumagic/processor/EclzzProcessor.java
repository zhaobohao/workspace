package com.complone.zhihumagic.processor;

import com.complone.zhihumagic.downloader.HttpClientDownloaderExtend;
import com.complone.zhihumagic.model.GithubUserInfo;
import com.complone.zhihumagic.pipeline.GithubUserPipeline;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Iterator;
import java.util.Map;

@Component("githubProcessor")
public class EclzzProcessor implements PageProcessor {


    private static final String start_url = "http://ss";
    private  int start=700;
    private  int max=800;

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
//            .setHttpProxy(new HttpHost("45.32.50.126",4399));


    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来

        page.putField("name", page.getHtml().xpath("//*[@id=\"m_link\"]/@value"));

        if (page.getResultItems().get("name")==null || StringUtils.isBlank(page.getResultItems().get("name").toString())) {
            if(start<=max)
            {
                start++;
                page.addTargetRequest(start_url.replaceAll("\\d{1,3}?\\.html",start+".html"));
                System.out.println(start_url.replaceAll("\\d{1,3}?\\.html",start+".html"));
            }
            //skip this page
            page.setSkip(true);
        }

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(http://www\\.eclzz\\.xyz/detail/.*\\.html)").all());


    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start(PageProcessor pageProcessor, Pipeline pipeline) {
        Spider.create(pageProcessor).addUrl(start_url).addPipeline(pipeline).thread(5).run();
    }

    public static void main(String[] args) {

        Spider spider = Spider.create(new EclzzProcessor())
                .addUrl(start_url)
                .addPipeline(new Pipeline(){
                    public void process(ResultItems resultItems, Task task) {
                        //System.out.println("get page: " + resultItems.getRequest().getUrl());
                        Iterator i$ = resultItems.getAll().entrySet().iterator();
                        while(i$.hasNext()) {
                            Map.Entry<String, Object> entry = (Map.Entry)i$.next();
                            //System.out.println((String)entry.getKey() + ":\t" + entry.getValue());
                            System.out.println( entry.getValue());
                        }
                    }
                })
                .setDownloader(new HttpClientDownloaderExtend(""))
                //开启5个线程抓取
                .thread(5);
        spider.run();
    }
}
