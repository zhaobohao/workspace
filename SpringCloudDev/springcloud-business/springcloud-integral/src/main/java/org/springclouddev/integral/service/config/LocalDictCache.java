package org.springclouddev.integral.service.config;

import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.integral.entity.DataDicPrm;
import org.springclouddev.integral.mapper.DataDicPrmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LocalDictCache {

    private static Map<String, Object> dataDicCacheMap = new HashMap<String, Object>();
    @Autowired
    private DataDicPrmMapper ddpMapper;

    @PostConstruct
    public void init() {
        dataDicCacheMap.clear();
        DataDicPrm example = new DataDicPrm();
        List<DataDicPrm> prms = ddpMapper.selectList(Condition.getQueryWrapper(new DataDicPrm()));
        for (DataDicPrm sysDataDicPrm : prms) {
            dataDicCacheMap.put(sysDataDicPrm.getCode().toString(), sysDataDicPrm);
        }
    }

    public static DataDicPrm getDicPrmById(String id) {

        DataDicPrm sysDataDicPrm = (DataDicPrm) dataDicCacheMap.get(id);
        ;
        return sysDataDicPrm;
    }
    public static String getDicNameById(String id) {
    	 DataDicPrm sysDataDicPrm = (DataDicPrm) dataDicCacheMap.get(id);
    	 if(sysDataDicPrm==null) {
    		 return null;
    	 }
    	 return sysDataDicPrm.getName();
    }

    public void reload() {
        init();
    }


}
