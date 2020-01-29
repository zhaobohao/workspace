package com.complone.zhihumagic.pipeline;

import com.complone.zhihumagic.mapper.GithubUserInfoMapper;
import com.complone.zhihumagic.model.GithubUserInfo;
import com.complone.zhihumagic.service.GithubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by complone on 2018/11/2.
 */
@Component("githubUserPipeline")
public class GithubUserPipeline implements Pipeline{

    @Autowired
    private GithubUserService githubUserService;
    private volatile int count = 0;

    @Override
    public void process(ResultItems resultItems, Task task) {
        GithubUserInfo githubUserInfo = resultItems.get("githubUserInfo");

        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
            if (entry.getKey() == "author"&& resultItems.get("author")==null){
                //防止github可能爬取到topic之类的链接，防止数据行为空
                    continue;
            }
        }
            githubUserService.insertGithubUserInfo(githubUserInfo);
            count++;
            System.out.println("已经插入第" + count + "条数据");
    }
}
