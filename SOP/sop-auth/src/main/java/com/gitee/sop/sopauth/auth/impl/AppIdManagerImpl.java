package com.gitee.sop.sopauth.auth.impl;

import com.gitee.sop.sopauth.auth.AppIdManager;
import com.gitee.sop.sopauth.entity.IsvInfo;
import com.gitee.sop.sopauth.mapper.IsvInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanghc
 */
@Service
@Slf4j
public class AppIdManagerImpl implements AppIdManager {

    public static final int FORBIDDEN = 2;
    @Autowired
    private IsvInfoMapper isvInfoMapper;

    @Override
    public boolean isValidAppId(String appId) {
        IsvInfo isvInfo = isvInfoMapper.getByColumn("app_key", appId);
        if (isvInfo == null) {
            return false;
        }
        if (isvInfo.getStatus().intValue() == FORBIDDEN) {
            log.error("appId已禁用:{}", appId);
            return false;
        }
        return true;
    }
}
