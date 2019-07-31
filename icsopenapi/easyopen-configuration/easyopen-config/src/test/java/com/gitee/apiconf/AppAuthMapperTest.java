package com.gitee.apiconf;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gitee.apiconf.mapper.ApiInfoMapper;
import com.gitee.easyopen.permission.ApiInfo;

public class AppAuthMapperTest extends BaseTest {

    @Autowired
    ApiInfoMapper appAuthMapper;
    
    @Test
    public void testDo() {
        List<ApiInfo> list = appAuthMapper.listAppAuth("app1");
        for (ApiInfo appAuth : list) {
            System.out.println(JSON.toJSONString(appAuth));
        }
    }
    
}
