package com.gitee.apiconf.service;

import com.gitee.apiconf.mapper.AppInfoMapper;
import com.gitee.fastmybatis.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanghc
 */
@Service
public class ApiInfoService {

    @Autowired
    AppInfoMapper appInfoMapper;

    public void removeApp(String app) {
        Query delQ = new Query().eq("app", app);
        appInfoMapper.deleteByQuery(delQ);
    }
}
