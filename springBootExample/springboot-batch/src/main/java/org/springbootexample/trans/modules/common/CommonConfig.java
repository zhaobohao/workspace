package org.springbootexample.trans.modules.common;

import com.alibaba.druid.pool.DruidDataSource;
import org.springbootexample.trans.modules.MyBeanValidator;
import org.springbootexample.trans.modules.MyJobListener;
import org.springbootexample.trans.modules.vtoll.BudgetVtoll;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * CsvBatchConfig
 *
 * @author zhaobo
 * @version 1.0
 * @since 2018/2/3
 */
@Configuration
public class CommonConfig {
    /**
     * ItemReader定义,用来读取数据
     * 1，使用FlatFileItemReader读取文件
     * 2，使用FlatFileItemReader的setResource方法设置csv文件的路径
     * 3，对此对cvs文件的数据和领域模型类做对应映射
     *
     * @return FlatFileItemReader
     */
    @Bean(name = "commonReader")
    @StepScope
    public FlatFileItemReader reader(@Value("#{jobParameters['input.file.name']}") String pathToFile,
                                     @Value("#{jobParameters['input.vo.name']}") String voName,
                                     @Value("#{jobParameters['input.columns']}") String columns) {
        FlatFileItemReader reader = new FlatFileItemReader();
//        reader.setResource(new ClassPathResource(pathToFile));
        reader.setResource(new FileSystemResource(pathToFile));
        reader.setLineMapper(new DefaultLineMapper() {
            {
                setLineTokenizer(new DelimitedLineTokenizer(",") {
                    {
                        setNames(columns.split(","));
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
                    try {
                        setTargetType(Class.forName(voName));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }});
            }
        });
        // 如果包含header，需要忽略掉
        reader.setLinesToSkip(1);
        return reader;
    }

    /**
     * ItemProcessor定义，用来处理数据
     *
     * @return
     */
    @Bean(name = "commonProcessor")
    @StepScope
    public ItemProcessor processor(@Value("#{jobParameters['input.vo.name']}") String voName) {
        //使用我们自定义的ItemProcessor的实现CsvItemProcessor
        Class c;
        try {
            c=Class.forName(voName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ValidatingItemProcessor<Object> processor = new ValidatingItemProcessor<Object> () {
            @Override
            public Object process(Object item) throws ValidationException {
                /*
                 * 需要执行super.process(item)才会调用自定义校验器
                 */
                super.process(item);
                /*
                 * 对数据进行简单的处理和转换 todo
                 */
                return item;
            }
        };
        //为processor指定校验器为CsvBeanValidator()
        processor.setValidator(csvBeanValidator());
        return processor;
    }

    /**
     * ItemWriter定义，用来输出数据
     * spring能让容器中已有的Bean以参数的形式注入，Spring Boot已经为我们定义了dataSource
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "commonWriter")
    @StepScope
    public ItemWriter writer(DruidDataSource dataSource,@Value("#{jobParameters['input.sq']}") String sql) {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        //我们使用JDBC批处理的JdbcBatchItemWriter来写数据到数据库
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());

        //在此设置要执行批处理的SQL语句
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }

    /**
     * Job定义，我们要实际执行的任务，包含一个或多个Step
     *
     * @param jobBuilderFactory
     * @param s1
     * @return
     */
    @Bean(name = "commonJob")
    public Job vtollJob(JobBuilderFactory jobBuilderFactory, @Qualifier("commonStep1") Step s1,
                        @Value("#{jobParameters['input.job.name']}") String jobname) {
        return jobBuilderFactory.get(jobname)
                .incrementer(new RunIdIncrementer())
                .flow(s1)//为Job指定Step
                .end()
                .listener(new MyJobListener())//绑定监听器csvJobListener
                .build();
    }

    /**
     * step步骤，包含ItemReader，ItemProcessor和ItemWriter
     *
     * @param stepBuilderFactory
     * @param reader
     * @param writer
     * @param processor
     * @return
     */
    @Bean(name = "commonStep1")
    public Step vtollStep1(StepBuilderFactory stepBuilderFactory,
                           @Qualifier("commonReader") ItemReader reader,
                           @Qualifier("commonWriter") ItemWriter writer,
                           @Qualifier("commonProcessor") ItemProcessor processor,
                           @Value("#{jobParameters['input.job.name']}") String jobname) {
        return stepBuilderFactory
                .get(jobname+"step1")
                .<BudgetVtoll, BudgetVtoll>chunk(5000)//批处理每次提交5000条数据
                .reader(reader)//给step绑定reader
                .processor(processor)//给step绑定processor
                .writer(writer)//给step绑定writer
                .faultTolerant()
                .retry(Exception.class)   // 重试
                .noRetry(ParseException.class)
                .retryLimit(1)           //每条记录重试一次
                .skip(Exception.class)
                .skipLimit(200)         //一共允许跳过200次异常
//                .taskExecutor(new SimpleAsyncTaskExecutor()) //设置每个Job通过并发方式执行，一般来讲一个Job就让它串行完成的好
//                .throttleLimit(10)        //并发任务数为 10,默认为4
                .build();
    }

    @Bean
    public Validator csvBeanValidator() {
        return new MyBeanValidator();
    }

}