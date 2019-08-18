package com.batch.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zhaoyiyang
 */
@Configuration
@EnableBatchProcessing
/**
 * JobRepository bean 名称 "jobRepository"
 * JobLauncher bean名称"jobLauncher"
 * JobRegistry bean名称"jobRegistry"
 * PlatformTransactionManager bean名称 "transactionManager"
 * JobBuilderFactory bean名称"jobBuilders"
 * StepBuilderFactory bean名称"stepBuilders"
 */
@Slf4j
public class BatchSystemConfiguration {


    /**
     * 多线程执行Step里的任务
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(50);
        threadPoolTaskExecutor.setMaxPoolSize(200);
        threadPoolTaskExecutor.setQueueCapacity(1000);
        threadPoolTaskExecutor.setThreadNamePrefix("Data-Job");
        return threadPoolTaskExecutor;
    }

    /**
     * 异步执行Step，这个实现不重用任何线程,每次调用都启动一个新线程
     * @return
     */
    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor()
    {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor=new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(20);
        return simpleAsyncTaskExecutor;
    }

    /**
     * 这个实现不会异步执行。相反，每次调用都在发起调用的线程中执行。它的主要用处是在不需要多线程的时候，比如简单的test case。
     * @return
     */
    @Bean
    public SyncTaskExecutor syncTaskExecutor()
    {
        return new SyncTaskExecutor();

    }




}
