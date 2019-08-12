package com.spring.web.test.impl;

import com.spring.web.test.BaseTest;
import jline.internal.Log;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class SubClassTest extends BaseTest {

    @Test
    public void test1()
    {
        System.out.println("the extends is useful!!");
        log.info("log is useful");
    }

}
