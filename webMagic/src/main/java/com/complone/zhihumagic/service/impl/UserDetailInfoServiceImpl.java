package com.complone.zhihumagic.service.impl;

import com.complone.zhihumagic.mapper.UserDetailInfoMapper;
import com.complone.zhihumagic.model.UserDetailInfo;
import com.complone.zhihumagic.service.UserDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by complone on 2018/11/4.
 */
@Service("userDetailInfoService")
public class UserDetailInfoServiceImpl implements UserDetailInfoService{

    @Autowired
    private UserDetailInfoMapper userDetailInfoMapper;


    @Override
    public void insertOne(UserDetailInfo userDetailInfo) {
        userDetailInfoMapper.insertOne(userDetailInfo);
    }
}
