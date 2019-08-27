package com.batch.test.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static com.batch.test.util.JobUtils.getSeed;

/**
 * 启动job的service ,
 * 需要配置spring.batch.job.enabled=false，防止启动应用的时候自动启动job
 */
@Service
@Slf4j
public class JobLaunchService {

    @Resource
    JobLauncher jobLauncher;

    @Resource(name = "fileJob1")
    Job fileSourceJob;

    public void launchSomeJob()
    {
        JobParameters jobParameter=new JobParametersBuilder()
                .addString("seed",getSeed(Boolean.FALSE))
                .addString("other key","val")
                .toJobParameters();
        try {
            jobLauncher.run(fileSourceJob,jobParameter);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

    }




}
