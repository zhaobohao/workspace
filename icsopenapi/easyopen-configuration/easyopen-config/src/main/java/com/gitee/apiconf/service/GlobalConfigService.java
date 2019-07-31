package com.gitee.apiconf.service;

import com.gitee.apiconf.common.ConfigField;
import com.gitee.apiconf.entity.GlobalConfig;
import com.gitee.apiconf.entity.GlobalEnum;
import com.gitee.apiconf.mapper.GlobalConfigMapper;
import com.gitee.fastmybatis.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GlobalConfigService {

    @Autowired
    GlobalConfigMapper globalConfigMapper;

    private Map<String, List<ConfigField>> configMap = new HashMap<>(16);

    public void init() {
        configMap.clear();
        List<GlobalConfig> list = globalConfigMapper.list(new Query());
        for (GlobalConfig globalConfig : list) {
            String keyName = globalConfig.getKeyName();
            List<ConfigField> fieldList = configMap.get(keyName);
            if (fieldList == null) {
                fieldList = new ArrayList<>();
                configMap.put(keyName, fieldList);
            }
            fieldList.add(new ConfigField(globalConfig.getFieldName(), globalConfig.getFieldValue()));
        }
    }

    public String getValue(GlobalEnum globalEnum) {
        Query query = new Query();
        query.eq("key_name", globalEnum.getKeyName())
                .eq("field_name", globalEnum.getFieldName());
        List<ConfigField> list = configMap.get(globalEnum.getKeyName());
        for (ConfigField configField : list) {
            if (globalEnum.getFieldName().equals(configField.getFieldName())) {
                return configField.getFieldValue();
            }
        }
        return null;
    }

}
