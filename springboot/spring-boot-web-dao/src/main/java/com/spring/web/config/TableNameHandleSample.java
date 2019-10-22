package com.spring.web.config;

import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 动态替换表名，适用于分区表及分库。
 */
public class TableNameHandleSample implements ITableNameHandler {
    @Override
    public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
        //把你的表名替换逻辑，写在这里。
        return null;
    }
}
