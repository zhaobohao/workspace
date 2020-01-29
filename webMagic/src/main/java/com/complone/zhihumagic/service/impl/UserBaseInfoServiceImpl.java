package com.complone.zhihumagic.service.impl;

import com.complone.zhihumagic.mapper.UserDetailInfoMapper;
import com.complone.zhihumagic.model.UserDetailInfo;
import com.complone.zhihumagic.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl implements UserBaseInfoService {

    @Autowired
    private UserDetailInfoMapper userDetailInfoMapper;

    public int getBaseUsersAccount(){
        UserDetailInfo record = new UserDetailInfo();
        record.setDetailUserId(11);
        return userDetailInfoMapper.selectCount(record);
    }

    @Override
    public void insertOne(UserDetailInfo userDetailInfo) {
        userDetailInfoMapper.insertOne(userDetailInfo);

    }
}
