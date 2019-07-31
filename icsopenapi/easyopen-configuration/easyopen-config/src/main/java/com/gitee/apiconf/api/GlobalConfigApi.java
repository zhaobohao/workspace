package com.gitee.apiconf.api;

import com.gitee.apiconf.common.MyMapperUtil;
import com.gitee.apiconf.entity.GlobalConfig;
import com.gitee.apiconf.mapper.GlobalConfigMapper;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.exception.ApiException;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.PageEasyui;
import org.springframework.beans.factory.annotation.Autowired;


@ApiService
public class GlobalConfigApi {

    @Autowired
    GlobalConfigMapper globalConfigMapper;

    @Api(name = "global.config.pagelist")
    PageEasyui<GlobalConfig> globalConfigPagelist() {
        Query query = new Query();
        return MyMapperUtil.queryToEasyui(globalConfigMapper, query);
    }

    @Api(name = "global.config.add")
    void globalConfigAdd(GlobalConfig globalConfig) {
        Query query = new Query();
        query.eq("key_name", globalConfig.getKeyName())
                .eq("field_name", globalConfig.getFieldName());
        long count = globalConfigMapper.getCount(query);
        if (count > 0) {
            throw new ApiException(globalConfig.getKeyName() + " " + globalConfig.getFieldName() + "已存在", "200");
        }
        globalConfigMapper.save(globalConfig);
    }

    @Api(name = "global.config.update")
    void globalConfigUpdate(GlobalConfig globalConfig) {
        GlobalConfig rec = globalConfigMapper.getById(globalConfig.getId());
        CopyUtil.copyPropertiesIgnoreNull(globalConfig, rec);
        globalConfigMapper.update(rec);
    }

}
