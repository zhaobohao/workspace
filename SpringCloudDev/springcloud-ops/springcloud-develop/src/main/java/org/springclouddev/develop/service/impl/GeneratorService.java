package org.springclouddev.develop.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.develop.entity.DbInstance;
import org.springclouddev.develop.entity.TableInfo;
import org.springclouddev.develop.mapper.DbInstanceMapper;
import org.springclouddev.develop.mapper.TableInfoMapper;
import org.springclouddev.develop.service.IGeneratorService;
import org.springclouddev.develop.utils.GeneratorUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 代码自动生成器
 *
 * @author zhaobo
 * @since 2019-12-17
 */
@Service
@Slf4j
@AllArgsConstructor
public class GeneratorService implements IGeneratorService {

    private TableInfoMapper tableInfoMapper;

    private DbInstanceMapper dbInstanceMapper;

    @Override
    public byte[] generatorDdlFile(String ids) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zip = new ZipOutputStream(outputStream)) {

            for (String id : ids.split(",")) {
                //查询表信息
                TableInfo table = tableInfoMapper.selectById(id);
                //查询数据账户信息
                DbInstance dbInstance = dbInstanceMapper.selectById(table.getDbInstanceId());
                //查询列信息
                List<TableInfo> columns = tableInfoMapper.selectList(Condition.getQueryWrapper(new TableInfo().setParentId(Long.valueOf(id))));
                //生成代码
                GeneratorUtils.generatorDdlCode(dbInstance, table, columns, zip);
            }
        }
        return outputStream.toByteArray();
    }
}
