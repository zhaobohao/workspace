package com.complone.zhihumagic.processor;

import com.complone.zhihumagic.downloader.HttpClientDownloaderExtend;
import com.complone.zhihumagic.mapper.GithubUserInfoMapper;
import com.complone.zhihumagic.model.GithubUserInfo;
import com.complone.zhihumagic.pipeline.GithubUserPipeline;
import com.complone.zhihumagic.service.GithubUserService;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@Component("githubProcessor")
public class GitHubProcessor implements PageProcessor {


    private static final String start_url = "https://github.com/code4craft";


    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
//            .setHttpProxy(new HttpHost("45.32.50.126",4399));

    GithubUserInfo githubUserInfo = new GithubUserInfo();

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getHtml().xpath("//h1[@class='vcard-names']/span[2]/text()").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='vcard-names']/span[1]/text()").toString());
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));


        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);

        }
        githubUserInfo.setAuthor(page.getHtml().xpath("//h1[@class='vcard-names']/span[2]/text()").toString());
        githubUserInfo.setNickname(page.getHtml().xpath("//h1[@class='vcard-names']/span[1]/text()").toString());

        System.out.println(githubUserInfo.getNickname() + " ------------------ "+githubUserInfo.getAuthor());
        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w-]+)").all());

        page.putField("githubUserInfo",githubUserInfo);
//        githubUserService.insertGithubUserInfo(githubUserInfo);

    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start(PageProcessor pageProcessor, Pipeline pipeline) {
        Spider.create(pageProcessor).addUrl(start_url).addPipeline(pipeline).thread(5).run();
    }

    public static void main(String[] args) {

        Spider spider = Spider.create(new GitHubProcessor())
                .addUrl(start_url)
                .addPipeline(new GithubUserPipeline())
                //从"https://github.com/code4craft"开始抓
                //开启5个线程抓取
                .thread(5);
        spider.run();
    }
}
