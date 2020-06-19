package org.springclouddev.log.service;

import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.log.core.util.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * className：RestApiService
 * description： TODO
 * time：2020-05-29.15:28
 *
 * @author Tank
 * @version 1.0.0
 */
@Service
@Slf4j
public class RestApiService {
    @Autowired
    FeignClientTest feignClientTest;

    private static ThreadPoolExecutor threadPoolExecutor
            = ThreadPoolUtil.getPool(4, 8, 5000);

    public String getIndex(){

        try {
            Integer.parseInt("aaa");
        } catch (NumberFormatException e) {
            log.error("error:{}",e);
        }
        threadPoolExecutor.execute(TtlRunnable.get(() -> {
            log.info("tankSay =》我是子线程的日志！");
        }));
       return  feignClientTest.testFeign(100);
    }
}
