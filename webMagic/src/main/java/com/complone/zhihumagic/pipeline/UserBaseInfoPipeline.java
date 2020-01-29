package com.complone.zhihumagic.pipeline;

import com.complone.zhihumagic.mapper.UserDetailInfoMapper;
import com.complone.zhihumagic.model.UserBaseInfo;
import com.complone.zhihumagic.model.UserDetailInfo;
import com.complone.zhihumagic.service.UserBaseInfoService;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;


@Component
public class UserBaseInfoPipeline implements Pipeline {

    Logger logger = LoggerFactory.getLogger(UserBaseInfoPipeline.class);


    private BlockingDeque<UserDetailInfo> blockingDeque;


    @Autowired
    private UserBaseInfoService userBaseInfoService;

//    @Override
////    public void process(ResultItems resultItems, Task task) {
////        UserDetailInfo record = new UserDetailInfo();
////        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
////            System.out.println(entry.getKey() + ":\t" + entry.getValue());
////            if (null != entry.getValue()) {
////                switch (entry.getKey()) {
////                    case "pageUrl":
////                        record.setPageurl(entry.getValue().toString());
////                        break;
////                    case "nickname":
////                        record.setNickname(entry.getValue().toString());
////                        break;
////                    case "business":
////                        record.setBusiness(entry.getValue().toString());
////                        break;
////                    case "employment":
////                        record.setEmployment(entry.getValue().toString());
////                        break;
////                    case "position":
////                        record.setPosition(entry.getValue().toString());
////                        break;
////                    case "gender":
////                        record.setGender(entry.getValue().toString());
////                        break;
////                    case "shares":
////                        record.setShares(Integer.valueOf((String) entry.getValue()));
////                        break;
////                    case "collecters":
////                        record.setCollecters(Integer.valueOf((String) entry.getValue()));
////                        break;
////                    case "education":
////                        record.setEducation(entry.getValue().toString());
////                        break;
////                    case "educationExtra":
////                        record.setEducationextra(entry.getValue().toString());
////                        break;
////                    case "status":
////                        record.setStatus(entry.getValue().toString());
////                        break;
////                    default:
////                        break;
////                }
////            }
////        }


    public UserBaseInfoPipeline(){}

    public UserBaseInfoPipeline(BlockingDeque<UserDetailInfo> blockingDeque) { this.blockingDeque = blockingDeque; }

    @Override
    public void process(ResultItems resultItems, Task task) {
        UserDetailInfo userDetailInfo = resultItems.get("userDetailInfo");

        System.out.println(userDetailInfo.getPageurl()+" ------------------ "+userDetailInfo.getNickname());
//        UserDetailInfo u1 = new UserDetailInfo();
//        u1.setPageurl("www.baidu.com");
//        u1.setNickname("v2ch");
//        int row =  userDetailInfoMapper.insertOne(u1);
//        System.out.println(row);
        blockingDeque.add(userDetailInfo);
    }





}
