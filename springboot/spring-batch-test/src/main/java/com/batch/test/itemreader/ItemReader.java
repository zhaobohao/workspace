package com.batch.test.itemreader;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * itemReader的工厂类，生产一些常用的itemReader
 */
public class ItemReader {

    public static JdbcCursorItemReader<List<String>> getJdbcCursorItemReader(String selectSql, DataSource ds,String items)
    {
        JdbcCursorItemReader itemReader=new JdbcCursorItemReader();
        itemReader.setDataSource(ds);
        itemReader.setSql(selectSql);
        itemReader.setFetchSize(2000);
//        itemReader.setRowMapper( new BeanPropertyRowMapper(){{
//            setMappedClass(Entity.class);
//        }});
        itemReader.setRowMapper(new RowMapper() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                List<String>  rows=new ArrayList<>(128);
                Arrays.stream(items.split(",")).forEach(columnName->{
                    try {
                        rows.add(resultSet.getString(columnName));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                });
                return rows;
            }
        });
        return itemReader;
    }



}
