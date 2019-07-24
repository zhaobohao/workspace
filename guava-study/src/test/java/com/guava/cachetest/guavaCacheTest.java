package com.guava.cachetest;

import com.google.common.cache.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Log4j2
public class guavaCacheTest {

    @Test
    public void testName() {
    }

    @Test
    public void cacheTest() {

        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            public String load(String key) throws Exception {
                Thread.sleep(1000); //休眠1s，模拟加载数据
                System.out.println(key + " is loaded from a cacheLoader!");
                return key + "'s value";
            }
        };
        RemovalListener<String, String> listener = new RemovalListener<String, String>() {
             public void onRemoval(RemovalNotification<String, String> notification) {
                            System.out.println("[" + notification.getKey() + ":" + notification.getValue() + "] is removed!");
                         }
          };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //设置存储的最大对象数目
                .maximumSize(20)
                //设置写入后最大过期时间
                .expireAfterWrite(3, TimeUnit.SECONDS)
//设置最后一次访问最大过期时间
                .expireAfterAccess(3, TimeUnit.SECONDS)
                //弱引用，当没有指针引用key或者value对象时，会被 gc回收。
                .weakKeys().weakValues()
                //开启统计信息开关
                .recordStats()
                //删除监听器
                .removalListener(listener)
                //加载自动加载器
                .build(loader);

        cache.put("key1","value1");
        cache.put("key2","value2");
        cache.put("key3","value3");
        cache.put("key4","value4");
        cache.put("key5","value5");
        cache.put("key6","value6");

        cache.invalidate("key1");
        log.info("key1 is {}",cache.getIfPresent("key1"));
        log.info("key2 is {}",cache.getIfPresent("key2"));
        log.info("key3 is {}",cache.getIfPresent("key3"));
        log.info("key4 is {}",cache.getIfPresent("key4"));
        log.info("key5 is {}",cache.getIfPresent("key5"));
        try {
            log.info("key5 is {}",((LoadingCache<String, String>) cache).get("key10"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        pringt statistic info
        log.info(cache.stats());

    }

}
