package com.complone.zhihumagic.task;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by complone on 2018/11/4.
 */
@Component
public class StartTask {

    private ExecutorService fixedThreadPool;

    //初始化线程池

    public void init(ExecutorService fixedThreadPool) {
        this.fixedThreadPool = fixedThreadPool;
    }

    public void execute(SavingTask savingTask) {
        if (!fixedThreadPool.isShutdown()) {
                fixedThreadPool.execute(savingTask);
        }
    }

}
