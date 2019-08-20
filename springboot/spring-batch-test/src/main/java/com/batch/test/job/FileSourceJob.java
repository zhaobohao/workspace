package com.batch.test.job;


import com.batch.test.enity.Access;
import com.batch.test.listener.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.io.Writer;

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
public class FileSourceJob {
    private static final Logger log = LoggerFactory.getLogger(FileSourceJob.class);
    @Resource
    private JobBuilderFactory jobBuilderFactory;    //用于构建JOB

    @Resource
    private StepBuilderFactory stepBuilderFactory;  //用于构建Step

    @Resource
    private EntityManagerFactory emf;           //注入实例化Factory 访问数据

    @Resource
    private JobListener jobListener;            //简单的JOB listener

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 一个简单基础的Job通常由一个或者多个Step组成
     */
    @Bean("fileJob1")
    public Job dataHandleJob() {
        return jobBuilderFactory.get("FileSourceJob")
                .incrementer(new RunIdIncrementer())
                .start(handleDataStep())    //start是JOB执行的第一个step
//                .next(xxxStep()).
//                .next(xxxStep()).
//                ...

                .listener(jobListener)      //设置了一个简单JobListener
                .build();
    }

    /**
     * 一个简单基础的Step主要分为三个部分
     * ItemReader : 用于读取数据
     * ItemProcessor : 用于处理数据
     * ItemWriter : 用于写数据
     */
    @Bean
    public Step handleDataStep() {
        return stepBuilderFactory.get("getData").
                <Access, Access>chunk(100)        // <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
                .faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(100).skip(Exception.class) //捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                .reader(getDataReader())         //指定ItemReader
                .processor(getDataProcessor())   //指定ItemProcessor
                .writer(txtItemWriter())        //指定ItemWriter
                //最大使用线程池
                .throttleLimit(2)
                .taskExecutor(taskExecutor)
                .exceptionHandler((context, throwable) -> log.error("Skipping record on file. cause={}", ((Exception) throwable).getCause()))
                .build();
    }

    @Bean
    public FlatFileItemReader<? extends Access> getDataReader() {
        //读取数据,这里可以用JPA,JDBC,JMS 等方式 读入数据
        FlatFileItemReader<Access> fr = new FlatFileItemReader<>();
        //将每个文件的第一行忽略掉。
        fr.setLinesToSkip(1);
        fr.setResource(new FileSystemResource("z:\\in.txt"));
        fr.setLineMapper(new DefaultLineMapper<Access>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"name", "age"});
                setDelimiter(",");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Access>() {{
                setTargetType(Access.class);
            }});
        }});
        return fr;
    }

    @Bean
    public ItemProcessor<Access, Access> getDataProcessor() {
        return new ItemProcessor<Access, Access>() {
            @Override
            public Access process(Access access) throws Exception {
                log.info("processor data : " + access.toString());  //模拟  假装处理数据,这里处理就是打印一下
                Thread.sleep(5000);
                return access;
            }
        };
    }


    @Bean
    public FlatFileItemWriter<Access> txtItemWriter() {
        FlatFileItemWriter<Access> txtItemWriter = new FlatFileItemWriter<>();
        txtItemWriter.setAppendAllowed(true);
        txtItemWriter.setEncoding("UTF-8");
        txtItemWriter.setResource(new FileSystemResource("z:\\out.txt"));
        txtItemWriter.setLineAggregator(new DelimitedLineAggregator<Access>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Access>() {{
                setNames(new String[]{"name", "age"});
            }});
        }});
        txtItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                //写入文件的头部信息
                writer.write("a,b,c,d");
            }
        });
        return txtItemWriter;
    }


}
