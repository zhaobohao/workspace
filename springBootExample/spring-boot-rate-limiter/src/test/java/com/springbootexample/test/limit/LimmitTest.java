package com.springbootexample.test.limit;

import cn.hutool.core.util.RandomUtil;
import com.springbootexample.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.springbootexample.limit.LimitParam;
import org.springbootexample.limit.RedisLimitManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.testng.annotations.Test;

@Slf4j
public class LimmitTest extends BaseTest {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    LimitParam limitParam;

    @Test
    public void limitRateTest() {
        RedisLimitManager rlm = new RedisLimitManager(redisTemplate);
        for (int i = 0; i < 100; i++) {

            if (rlm.acquire("test", limitParam)) {
                System.out.println("the business is runing !");
            } else {
                System.out.println("the business is blocked!");
            }
            try {
                Thread.sleep(RandomUtil.randomLong(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
