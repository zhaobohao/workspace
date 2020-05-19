package com.springclouddev.loges.demo.service;


import com.alibaba.ttl.threadpool.TtlExecutors;
import com.springclouddev.loges.core.LogMessage;
import com.springclouddev.loges.core.util.LogExceptionStackTrace;
import com.springclouddev.loges.trace.annotation.Trace;
import org.slf4j.LoggerFactory;
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
        logger.info("I am service! 下面调用EasyLogDubboService远程服务！");
        logger.info("远程调用成功！");
        tankService.tankSay(data);
        executorService.execute(() -> {
            logger.info("子线程日志展示");
        });
        try {

            LogMessage lo=null;
            lo.setMethod("");
        }catch (Exception e){
            logger.error("异常日志展示：{}", LogExceptionStackTrace.erroStackTrace(e));
        }
        logger.warn("警告日志展示！");
    }
}
