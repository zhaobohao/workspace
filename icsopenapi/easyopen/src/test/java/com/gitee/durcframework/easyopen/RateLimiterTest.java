package com.gitee.durcframework.easyopen;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;

import junit.framework.TestCase;

public class RateLimiterTest extends TestCase {

    @Test
    public void testRateLimiter() {
        long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(5);
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        System.out.println(limiter.acquire());
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "ms");
    }
    
}
