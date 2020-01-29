package com.complone.zhihumagic.service.impl;

import com.complone.zhihumagic.mapper.GithubUserInfoMapper;
import com.complone.zhihumagic.model.GithubUserInfo;
import com.complone.zhihumagic.service.GithubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by complone 2018/11/2.
 */
@Service("githubUserService")
public class GtihubUserServiceImpl implements GithubUserService {

    @Autowired
    private GithubUserInfoMapper githubUserInfoMapper;

    @Override
    public void insertGithubUserInfo(GithubUserInfo githubUserInfo) {
        githubUserInfoMapper.insertGithubUserInfo(githubUserInfo);
    }
}
