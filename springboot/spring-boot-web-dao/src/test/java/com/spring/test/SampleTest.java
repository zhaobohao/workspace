package com.spring.test;

import com.spring.BaseTest;
import com.spring.web.dao.entity.User;
import com.spring.web.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Slf4j
public class SampleTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void mybatisPlusSimpleTest()
    {
        log.info("we begin mybatis plus test ----");
        List<User> users= userMapper.selectList(null);
        Assert.assertEquals(users.size(),5);
        users.stream().forEach(u-> System.out.println(u.toString()));
    }

}
