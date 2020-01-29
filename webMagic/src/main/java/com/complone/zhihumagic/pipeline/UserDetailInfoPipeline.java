package com.complone.zhihumagic.pipeline;

import com.complone.zhihumagic.model.UserDetailInfo;
import com.complone.zhihumagic.service.GithubUserService;
import com.complone.zhihumagic.service.UserBaseInfoService;
import com.complone.zhihumagic.service.UserDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by complone on 2018/11/4.
 */
@Component("userDetailInfoPipeline")
public class UserDetailInfoPipeline implements Pipeline{

    @Autowired
    private UserDetailInfoService userDetailInfoService;
    private volatile int count = 0;

    @Override
    public void process(ResultItems resultItems, Task task) {

        UserDetailInfo userDetailInfo = resultItems.get("userDetailInfo");
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
            if (entry.getKey() == "userDetailInfo"){

            }
        }
        System.out.println(userDetailInfo.getNickname()+" ---------------- "+userDetailInfo.getPageurl());
        userDetailInfoService.insertOne(userDetailInfo);
        ++count;
        System.out.println("已经插入第"+count+"条数据");
    }
}
