package com.batch.test.itemwriter;

import com.batch.test.util.JobUtils;
import com.google.common.base.Charsets;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * itemWriter的工厂类，生成一些常用的writer
 */
public class ItemWriterFactory {

    /**
     *
     * @param regexPath
     * @param items
     * @return
     */
    public static FlatFileItemWriter<List<String>> getFlatFileItemWriter(@NotNull String regexPath, @NotNull String items)
    {
        FlatFileItemWriter writer=new FlatFileItemWriter();
        writer.setAppendAllowed(true);
        writer.setEncoding(Charsets.UTF_8.name());
        writer.setResource(JobUtils.getResources(regexPath)[0]);
        writer.setLineAggregator(new DelimitedLineAggregator<List<String>>(){{
         setDelimiter(",");
         setFieldExtractor(strings -> strings.toArray());
//         setFieldExtractor(new BeanWrapperFieldExtractor<>());
        }});
        writer.setHeaderCallback(writer1 -> writer1.write(items));


        return writer;
    }



}
