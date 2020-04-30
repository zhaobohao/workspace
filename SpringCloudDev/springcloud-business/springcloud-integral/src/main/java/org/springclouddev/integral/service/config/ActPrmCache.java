package org.springclouddev.integral.service.config;

import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.integral.entity.ActPrm;
import org.springclouddev.integral.mapper.ActPrmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActPrmCache {
	private static Map<String, Object> actPrmCacheMap=new HashMap<String, Object>();
//	private static Map<String, Object> actPrmCateCacheMap=new HashMap<String, Object>();
	
	@Autowired
	private ActPrmMapper actPrmMapper;
//	@Autowired
//	private ActPrmCateMapper actPrmCateMapper;
	
	@PostConstruct
	public void init() {
		actPrmCacheMap.clear();
//		actPrmCateCacheMap.clear();
		ActPrm example=new ActPrm();
//		ActPrmCateExample example2=new ActPrmCateExample();
		List<ActPrm> prms=actPrmMapper.selectList(Condition.getQueryWrapper(new ActPrm()));
//		List<ActPrmCate> prmCates=actPrmCateMapper.selectByExample(example2);
		for(ActPrm actPrm:prms) {
			actPrmCacheMap.put(actPrm.getCode(),actPrm);
		}
//		for(ActPrmCate actPrmCate:prmCates) {
//			actPrmCateCacheMap.put(actPrmCate.getCode(), actPrmCate);
//		}
	}
	public static ActPrm getActPrmById(String id) {
		return (ActPrm)actPrmCacheMap.get(id);
	}
	
	public static String getActPrmNameById(String id) {
		if(actPrmCacheMap.get(id)==null) {
			return null;
		}
		return ((ActPrm)actPrmCacheMap.get(id)).getName();
	}
	
	public void  reload() {
		init();
	}
}
