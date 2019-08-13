package com.spring.web.test.impl;

import com.spring.web.test.BaseTest;
import com.spring.web.test.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import javax.annotation.Resource;

@Slf4j
public class AsyncTest extends BaseTest {


    @Resource
    CacheService cacheService;

    @Test
    public void asyncTestUnit() {
        for (int i = 0; i < 100; i++) {
            System.out.println(cacheService.getNameWithoutCache());
        }
    }

}
