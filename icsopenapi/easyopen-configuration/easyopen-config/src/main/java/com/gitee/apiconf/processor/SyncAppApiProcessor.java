package com.gitee.apiconf.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gitee.apiconf.common.SpringContext;
import com.gitee.apiconf.entity.LimitAppConfig;
import com.gitee.apiconf.entity.PermApiInfo;
import com.gitee.apiconf.entity.status.ApiInfoStatus;
import com.gitee.apiconf.mapper.PermApiInfoMapper;
import com.gitee.easyopen.bean.Api;
import com.gitee.easyopen.config.ConfigMsg;
import com.gitee.fastmybatis.core.query.Query;
import io.netty.channel.Channel;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncAppApiProcessor extends AbstractNettyServerProcessor {

    @Override
    public void process(Channel channel, ConfigMsg msg) {
        PermApiInfoMapper apiInfoMapper = SpringContext.getBean(PermApiInfoMapper.class);
        JSONArray arrJson = JSON.parseArray(msg.getData());
        String app = msg.getApp();

        List<PermApiInfo> toBeSave = new ArrayList<>();
        List<LimitAppConfig> tobeSaveLimit = new ArrayList<>();
        List<Long> remainApiIdList = this.listAllApiId(apiInfoMapper, app);
        
        for (int i = 0; i < arrJson.size(); i++) {
            Api api = JSON.parseObject(arrJson.getString(i), Api.class);
            Query query = new Query();
            query.eq("app", app).eq("name", api.getName()).eq("version", api.getVersion());
            PermApiInfo rec = apiInfoMapper.getByQuery(query);
            // 如果数据库没有,保存到数据库
            if(rec == null) {
                rec = new PermApiInfo();
                rec.setApp(msg.getApp());
                rec.setName(api.getName());
                rec.setVersion(api.getVersion());
                rec.setDescription(api.getDescription());
                rec.setModuleName(api.getModuleName());
                rec.setOrderIndex(api.getOrderIndex());
                rec.setStatus(ApiInfoStatus.USING.getStatus());
                toBeSave.add(rec);
            } else {
                // 更新描述
                PermApiInfo updateBean = new PermApiInfo();
                updateBean.setId(rec.getId());
                updateBean.setDescription(api.getDescription());
                updateBean.setModuleName(api.getModuleName());
                updateBean.setOrderIndex(api.getOrderIndex());
                updateBean.setStatus(ApiInfoStatus.USING.getStatus());

                apiInfoMapper.updateIgnoreNull(updateBean);

            	remainApiIdList.remove(rec.getId());
            }
        }

        // 客户端新增的接口，批量保存
        if(CollectionUtils.isNotEmpty(toBeSave)) {
            apiInfoMapper.saveBatch(toBeSave);
        }

        // 剩下的id表示客户端已经删除,配置中心还存在,标记未使用
        if(CollectionUtils.isNotEmpty(remainApiIdList)) {
            Map<String, Object> updateValue = new HashMap<>();
            updateValue.put("status", ApiInfoStatus.UN_USED.getStatus());
            apiInfoMapper.updateByQuery(updateValue, new Query().in("id", remainApiIdList));
        }
    }


    private List<Long> listAllApiId(PermApiInfoMapper apiInfoMapper,String app) {
        Query query = new Query();
        query.eq("app", app);
        List<PermApiInfo> allApi = apiInfoMapper.list(query);
        List<Long> ret = new ArrayList<>(allApi.size());
        for (PermApiInfo permApiInfo : allApi) {
            ret.add(permApiInfo.getId());
        }
        return ret;
    }

}
