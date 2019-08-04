package com.batch.test;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class SpringBatchApplication {

    public static void main(String[] args) throws BeansException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, InterruptedException, IOException {
        boolean oneJob = false;
        boolean twoJob = false;

        SpringApplication application = new SpringApplication(SpringBatchApplication.class);
        ConfigurableApplicationContext ctx = application.run(args);

        JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
        /*System.out.println("job1 begin ");
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();
        jobLauncher.run(ctx.getBean("fileJob1", Job.class), jobParameters);
        //jobOne名称必须和JobOneConfiguration中配置的@bean Job 的方法名一致，后面jobTwo也是一样。
        System.out.println("job1 end ");

        //测试证明，多个job按顺序启动是阻塞的。
        System.out.println("job2 begin ");

        JobParameters jobParameters2 = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();
        jobLauncher.run(ctx.getBean("fileJob2", Job.class), jobParameters2);
        System.out.println("job2 end ");

        //在一次执行已经完成的job.这里要送不同的jobParameters进去。
        System.out.println("job3 begin ");
        jobLauncher.run(ctx.getBean("fileJob1", Job.class), new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters());
        jobLauncher.run(ctx.getBean("fileJob2", Job.class), new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters());
        System.out.println("job3 begin ");*/

        System.out.println("peoplejob begin ");
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .addString("path", "z:\\r.txt")
                .addString("items","name,age")
                .addString("batchInsertsql","insert into alipaytrando"
                        + "(tranId,channel,tranType,counterparty,goods,amount,isDebitCredit,state) values"
                        + "(:tranId,:channel,:tranType,:counterparty,:goods,:amount,:isDebitCredit,:state) ")
                .toJobParameters();
        jobLauncher.run(ctx.getBean("peoplejob",Job.class), jobParameters);
        //jobOne名称必须和JobOneConfiguration中配置的@bean Job 的方法名一致，后面jobTwo也是一样。
        System.out.println("peoplejob end ");


        System.out.println("accessjob begin ");

        jobLauncher.run(ctx.getBean("accessjob",Job.class), jobParameters);
        //jobOne名称必须和JobOneConfiguration中配置的@bean Job 的方法名一致，后面jobTwo也是一样。
        System.out.println("accessjob end ");



//      ctx.getBeansOfType(Job.class).forEach((k,v)-> System.out.println("+++"+v.getName()));

        System.out.println(Arrays.toString(ctx.getBeanNamesForType(Job.class)));
        System.exit(0);

    }
}

