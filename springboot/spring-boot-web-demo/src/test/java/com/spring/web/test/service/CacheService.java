package com.spring.web.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service

@Slf4j
public class CacheService {

    @Cacheable( cacheNames = "master")
    public String getName()
    {
      log.info("now we set a cache getName");
        return "zhaobo";
    }

    @Async
    public String getNameWithoutCache()
    {
        log.info("now we get a name without cache");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "zhaobo";
    }
}
