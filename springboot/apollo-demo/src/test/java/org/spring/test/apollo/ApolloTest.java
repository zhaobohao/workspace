package org.spring.test.apollo;


import lombok.extern.slf4j.Slf4j;
import org.spring.test.BaseTest;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @ApolloConfig用来自动注入Config对象 也可以用@ApolloConfig("name.space")将指定前缀的配置注入到config
 * @ApolloConfigChangeListener用来自动注册ConfigChangeListener
 * @ApolloJsonValue用来把配置的json字符串自动注入为对象
 *
 * 下面两个注解同时使用
 * @Configuration
 * @ConfigurationProperties(prefix = "redis.cache")
 *
 * 单独使用,向下面一样
 * @Configuration
 * @value(${timeout:100})
 */
@Slf4j
public class ApolloTest extends BaseTest {

    @Value("${spring.boot.timeout:100}")
    String timeout;
    @Test
    public void testKey()
    {
        log.debug("this is debug message");
        log.info("this is a info message");

        Assert.assertEquals("1000",timeout);
    }

}
