package com.springbootexample.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springbootexample.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// 指定启动类
@Slf4j
public class BaseTest extends AbstractTestNGSpringContextTests {
}
