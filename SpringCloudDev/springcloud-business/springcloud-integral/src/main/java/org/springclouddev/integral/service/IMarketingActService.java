package org.springclouddev.integral.service;


import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.MarketingAct;

import java.util.List;

public interface IMarketingActService extends BaseService<MarketingAct> {
	MarketingAct queryById(Long id);
	public void  updateMarketingActById(MarketingAct mktAct);
	public void deleteMarketingActById(Long actId);
	public List<MarketingAct>  getMarketingActList(MarketingAct example);
	public int insertMarketingAct(MarketingAct record);
	public int updMarketingAct(MarketingAct record, MarketingAct example);
	
	long getStatusCount(String status);
}
