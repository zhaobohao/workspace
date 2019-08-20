package com.batch.test.job;

import com.batch.test.enity.Access;
import com.batch.test.listener.JobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Slf4j
public class FromeFileToDBJob {

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
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Bean
    public Step presitenceDataStep() {
        return stepBuilderFactory.get("presitenceDataStep")
                // <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
                .<Access, Access>chunk(100)
                //捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                .faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(100).skip(Exception.class)
                //指定ItemReader
                .reader(fileSourceDataReader(null, null))
                //指定ItemProcessor
                .processor(fileSourceDataProcessor())
                //指定ItemWriter
                .writer(fileSourceDataItemWriter(null))
                //最大使用线程池
                .throttleLimit(2)
                .taskExecutor(taskExecutor)
                .exceptionHandler((context, throwable) -> log.error("Skipping record on file. cause={}", ((Exception) throwable).getCause()))
                .build();
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
        fr.setLinesToSkip(1);
        fr.setSkippedLinesCallback(s -> {
            log.info("we skip line :{}", s);
        });
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


}
