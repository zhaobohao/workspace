package org.springclouddev.integral.service.impl;

import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.integral.entity.LatestActCode;
import org.springclouddev.integral.entity.MarketingAct;
import org.springclouddev.integral.mapper.LatestActCodeMapper;
import org.springclouddev.integral.mapper.MarketingActMapper;
import org.springclouddev.integral.service.IMarketingActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MarketingActService extends BaseServiceImpl<MarketingActMapper, MarketingAct> implements IMarketingActService {
	@Autowired
	private MarketingActMapper mktActMapper;
	
	@Override
	public MarketingAct queryById(Long actId){
		return  mktActMapper.selectById(actId);
	}
	@Override
	public void updateMarketingActById(MarketingAct mktAct){
		//更新成功
		mktActMapper.updateById(mktAct);
	}
	
	@Override
	public void deleteMarketingActById(Long actId){
		mktActMapper.deleteById(actId);
	}
	@Override
	public List<MarketingAct> getMarketingActList(MarketingAct example) {
		return mktActMapper.selectList(Condition.getQueryWrapper(example));
		
	}
	
	@Override
	public int insertMarketingAct(MarketingAct record){
		return mktActMapper.insert(record);
	}
	@Override
	public int updMarketingAct(MarketingAct record,MarketingAct example){
		return mktActMapper.update(record, Condition.getQueryWrapper(example));
	}
	@Override
	public long getStatusCount(String status) {
		return mktActMapper.selectCount(Condition.getQueryWrapper(new MarketingAct().setActStatus(status)));
	}
}
