package com.complone.zhihumagic.service;

import com.complone.zhihumagic.model.UserDetailInfo;

public interface UserBaseInfoService {
    public int getBaseUsersAccount();

    public void insertOne(UserDetailInfo userDetailInfo);
}
