package org.springclouddev.log.demo.service;

import com.alibaba.ttl.TtlRunnable;
import org.springclouddev.log.core.TraceId;
import org.springclouddev.log.core.util.ThreadPoolUtil;
import org.slf4j.LoggerFactory;
import org.springclouddev.log.trace.annotation.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * className：TankService
 * description：
 * time：2020-05-11.14:06
 *
 * @author Tank
 * @version 1.0.0
 */
@Service
public class TankService {
    private static ThreadPoolExecutor threadPoolExecutor
            = ThreadPoolUtil.getPool(4, 8, 5000);
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TankService.class);
    @Autowired
    TankServiceTwo tankServiceTwo;

    @Autowired
    TankServiceThere tankServiceThere;

    @Trace
    public void tankSay(String kk)  {
        tankServiceTwo.tankServiceTwo(kk);
        tankServiceThere.tankServiceThere(kk);
      threadPoolExecutor.execute(TtlRunnable.get(() -> {
            TraceId.logTraceID.get();
            logger.info("tankSay =》我是子线程的日志！{}", TraceId.logTraceID.get());
        }));
    }
}
