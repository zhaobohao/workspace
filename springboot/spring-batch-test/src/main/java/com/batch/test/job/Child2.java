package com.batch.test.job;

import com.batch.test.enity.Access;
import com.batch.test.enity.People;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class Child2 extends FromeFileToDBJob<People> {
    /**
     * 一个简单基础的Job通常由一个或者多个Step组成
     */
    @Bean("peoplejob")
    public Job createFromFileToDBJob() {
        return jobBuilderFactory.get("DownLoadFileJOB"+this.classt.getName()).
                incrementer(new RunIdIncrementer()).
                start(downloadfiletep()).    //start是JOB执行的第一个step
                next(presitenceDataStep()).
//                next(xxxStep()).
//                next(xxxStep()).
//                ...

        listener(jobListener).      //设置了一个简单JobListener
                build();
    }

}
