package org.springclouddev.log.demo.service;


import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springclouddev.log.core.LogMessage;
import org.slf4j.LoggerFactory;
import org.springclouddev.log.trace.annotation.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class MainService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MainService.class);
    private static ExecutorService executorService = TtlExecutors.getTtlExecutorService(
            new ThreadPoolExecutor(8, 8,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>()));
    @Autowired
    TankService tankService;

    @Trace
    public void testLog(String data) {
        logger.info("远程调用成功！");

        executorService.execute(() -> {
            logger.info("子线程日志展示");
        });
        try {
           // LogMessage lo=null;
            //lo.setMethod("");
            tankService.tankSay(data);

        }catch (Exception e){
            logger.error("异常日志展示");
        }
        logger.warn("警告日志展示！");
    }
}
