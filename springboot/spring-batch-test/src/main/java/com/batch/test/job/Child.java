package com.batch.test.job;

import cn.hutool.core.io.FileUtil;
import com.batch.test.enity.Access;
import com.batch.test.listener.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;

@Configuration
@EnableBatchProcessing
public class Child  {
    private static final Logger log = LoggerFactory.getLogger(FromeFileToDBJob.class);
    @Resource
    protected JobBuilderFactory jobBuilderFactory;    //用于构建JOB

    @Resource
    protected StepBuilderFactory stepBuilderFactory;  //用于构建Step

    @Resource
    protected EntityManagerFactory emf;           //注入实例化Factory 访问数据

    @Resource
    protected JobListener jobListener;            //简单的JOB listener

    @Autowired
    protected DataSource dataSource;

    /**
     * 一个简单基础的Job通常由一个或者多个Step组成
     */
    @Bean("accessjob")
    public Job createFromFileToDBJob() {
        return jobBuilderFactory.get("DownLoadFileJOBAccess").
                incrementer(new RunIdIncrementer()).
                start(downloadfiletep()).    //start是JOB执行的第一个step
                next(presitenceDataStep()).
//                next(xxxStep()).
//                next(xxxStep()).
//                ...

        listener(jobListener).      //设置了一个简单JobListener
                build();
    }

    @Bean
    public Step presitenceDataStep() {
        return stepBuilderFactory.get("presitenceDataStep").
                // <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
                        <Access, Access>chunk(100).
                //捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                        faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(100).skip(Exception.class).
                //指定ItemReader
                        reader(fileSourceDataReader(null, null)).
                //指定ItemProcessor
                        processor(fileSourceDataProcessor()).
                //指定ItemWriter
                        writer(fileSourceDataItemWriter(null)).
                        build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Access> fileSourceDataItemWriter(@Value("#{jobParameters[batchInsertsql ]}") String batchInsertsql) {
        JdbcBatchItemWriter<Access> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql(batchInsertsql);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Access>());
        return writer;
    }

    @Bean
    @StepScope
    public ItemProcessor<Access, Access> fileSourceDataProcessor() {
        return (ItemProcessor<Access, Access>) access -> {
            return access;
        };
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Access> fileSourceDataReader(@Value("#{jobParameters[path ]}") String path, @Value("#{jobParameters[items ]}") String items) {

        //读取数据,这里可以用JPA,JDBC,JMS 等方式 读入数据
        FlatFileItemReader<Access> fr = new FlatFileItemReader<Access>();

        fr.setResource(new FileSystemResource(path));
        fr.setLineMapper(new DefaultLineMapper<Access>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(items.split(","));
                setDelimiter(",");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Access>() {{
                setTargetType(Access.class);
            }});
        }});
        return fr;
    }

    /**
     * 一个简单基础的Step主要分为三个部分
     * ItemReader : 用于读取数据
     * ItemProcessor : 用于处理数据
     * ItemWriter : 用于写数据
     */
    @Bean
    public Step downloadfiletep() {
        return stepBuilderFactory.get("downloadfiletep").
                // <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
                        <String, String>chunk(1).
                //捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                        faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(100).skip(Exception.class).
                //指定ItemReader
                        reader(getDataReader(null)).
                //指定ItemProcessor
                        processor(downloadFileProcessor()).
                //指定ItemWriter
                        writer(downloadfileItemWriter(null)).
                        build();
    }

    @Bean
    @StepScope
    public ItemWriter<? super String> downloadfileItemWriter(@Value("#{jobParameters[path]}") String path) {
        return (ItemWriter<String>) list -> {
            //do nothing

            System.out.println("write it once " + path);
        };

    }

    @Bean
    @StepScope
    public ItemProcessor<? super String, ? extends String> downloadFileProcessor() {
        return (ItemProcessor<String, String>) path -> {

            //you can downloadfile here;
            FileUtil.touch(path + ".tmp");
            //rename
            FileUtil.rename(new File(path + ".tmp"), FileUtil.getName(path), false, false);
            return "success";
        };

    }

    @Bean("filegetdata")
    @StepScope
    public ItemReader<? extends String> getDataReader(@Value("#{jobParameters[path ]}") String path) {

        return (ItemReader<String>) () -> {
            return FileUtil.exist(path) ? null : path;

        };
    }

}
