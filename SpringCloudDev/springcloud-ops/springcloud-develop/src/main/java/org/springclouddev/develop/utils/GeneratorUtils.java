package org.springclouddev.develop.utils;

import org.apache.commons.io.IOUtils;
import org.springclouddev.develop.entity.DbInstance;
import org.springclouddev.develop.entity.TableInfo;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zhaoyiyang
 * @since
 */
public class GeneratorUtils {
    /**
     * 生成ddl代码
      * @param dbInstance
     * @param table
     * @param columns
     * @param zip
     */
    public static void generatorDdlCode(DbInstance dbInstance, TableInfo table, List<TableInfo> columns, ZipOutputStream zip) throws Exception {
        //渲染模板
        try(StringWriter sw = new StringWriter()){
        // 开始生成ddl文件内容
        // 1.开始输出 创建表的语句
        sw.append("create table ").append(" ").append(table.getName()).append("\n");
        sw.append("{\n");
        // 2.生成字段语句
        sw.append(table.getName()).append("Id").append("    ").append("varchar2(64 char) NOT NULL,\n");
        columns.forEach(column -> {
            sw.append(column.getName()).append("   ")
                    .append(column.getTypeKey())
                    .append("(").append(column.getTypeValue())
                    .append("varchar2".equals(column.getTypeKey())?" CHAR":" ")
                    .append(") ")
                    .append(column.getIsEmpty()==2?"NOT NULL":"")
                    .append(StringUtils.isEmpty(column.getDefaultValue())?"":" default ")
                    .append(StringUtils.isEmpty(column.getDefaultValue())?"": column.getDefaultValue())
                    .append(",\n");
        });
        sw.append("created_by varchar2(50 char),\n");
        sw.append("created_date timestamp default systimestamp,\n");
        sw.append("updated_by varchar2(50 char),\n");
        sw.append("updated_date timestamp default systimestamp,\n");
        sw.append("is_deleted int(2) default 0\n");
        sw.append("}INITRANS 6;\n");
        //创建表的同义词
        sw.append("create or replace public synonym ").append(table.getName()).append(" for ").append(dbInstance.getDataUser()).append(".").append(table.getName()).append(";\n");
        //先创建索引，在创建主键
        sw.append("create unique index PK_").append(table.getName()).append("_ID").append(" on ").append(dbInstance.getDataUser()).append(".").append(table.getName()).append("(").append(table.getName()).append("Id").append(");\n");
        sw.append("alter table ").append(dbInstance.getDataUser()).append(".").append(table.getName()).append(" add constraint PK_").append(table.getName()).append("_ID").append(" primary key(").append(table.getName()).append("Id) using index ").append("PK_").append(table.getName()).append("_ID;\n");
        //创建注解
        sw.append("comment on table ").append(table.getName()).append(" is ").append("'").append(table.getComment()).append("';\n");
        columns.forEach(column -> {
            sw.append("commont on column ").append(table.getName()).append(".").append(column.getName()).append(" is ").append("'").append(column.getComment()).append("';\n");
        });
        // 进行授权
        sw.append("grant select,insert,update,delete on ").append(table.getName()).append(" to ").append(dbInstance.getEtlUser()).append(";\n");
        sw.append("grant select,insert,update,delete on ").append(table.getName()).append(" to ").append(dbInstance.getOprUser()).append(";\n");
        sw.append("grant select,insert,update,delete on ").append(table.getName()).append(" to ").append(dbInstance.getRptUser()).append(";\n");
        //创建sequnce
        sw.append("create sequence").append(dbInstance.getDataUser()).append(".SEQ_").append(table.getName()).append("\n")
                .append("minvalue 1\n")
                .append("maxvalue 9999999999999999999999999999\n")
                .append("start with 1\n")
                .append("increment by 1\n")
                .append("noorder\n")
                .append("cycle\n")
                .append("cache 1000;\n");
         //创建sequence的同议词
        sw.append("create or replace public synonym SEQ_").append(table.getName()).append(" for SEQ_").append(table.getName()).append(";\n");
        // 对sequence进行授权
        sw.append("grant select on SEQ_").append(table.getName()).append(" to ").append(dbInstance.getEtlUser()).append(";\n");
        sw.append("grant select on SEQ_").append(table.getName()).append(" to ").append(dbInstance.getOprUser()).append(";\n");
        sw.append("grant select on SEQ_").append(table.getName()).append(" to ").append(dbInstance.getRptUser()).append(";\n");
        // 生成内容结束
        try {
            //添加到zip
            zip.putNextEntry(new ZipEntry(table.getName()+"ddl.sql"));
            IOUtils.write(sw.toString(), zip, "UTF-8");
            zip.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException("渲染模板失败，表名：" + table.getName(), e);
        }}
    }
}
