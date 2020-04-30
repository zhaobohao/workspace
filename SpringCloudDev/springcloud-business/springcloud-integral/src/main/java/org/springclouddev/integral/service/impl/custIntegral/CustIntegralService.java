package org.springclouddev.integral.service.impl.custIntegral;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.integral.entity.*;
import org.springclouddev.integral.mapper.*;
import org.springclouddev.integral.service.custIntegral.ICustIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustIntegralService extends BaseServiceImpl<CustIntegralDetailMapper, CustIntegralDetail> implements ICustIntegralService {
	@Autowired
	private AccountIntegralDetailMapper accountIntegralMapper;
	@Autowired
	private IntegralAdjustActionMapper integralAdjusActionMapper;
	@Autowired
	private IntegralAdjustMapper integralAdjustMapper;
	@Autowired
	private IntegralStatusActionMapper integralStatusActionMapper;
	@Override
	public  List<CustIntegralDetail> getCustIntegralInfo(CustIntegralDetail  example){
		return baseMapper.selectList(Condition.getQueryWrapper(example));
	}
	
	@Override
	public List<AccountIntegralDetail> getAccountIntegralDetail(AccountIntegralDetail example){
		return accountIntegralMapper.selectList(Condition.getQueryWrapper(example).lambda().like(AccountIntegralDetail::getChangeIntegral,"-"));
	}
	
	@Override
	public List<CustIntegralDetail> selectCustIntegralDetailByExample(CustIntegralDetail example){
		return baseMapper.selectList(Condition.getQueryWrapper(example));
	}
	
	@Override
	public void insertIntegralAdjust(IntegralAdjust record){
		integralAdjustMapper.insert(record);
	}
	
	@Override
	public void insertIntegralAdjustAction(IntegralAdjustAction record){
		integralAdjusActionMapper.insert(record);
	}
	
	@Override
	public List<AccountIntegralDetail> selectAccountIntegralDetailByExample(AccountIntegralDetail example){
		return accountIntegralMapper.selectList(Condition.getQueryWrapper(example).orderByDesc("id"));
	}
	
	@Override
	public void updateCustIntegralDetailByExampleSelective(@Param("record") CustIntegralDetail record, @Param("example") CustIntegralDetail example){
		baseMapper.update(record,Condition.getQueryWrapper(example));
	}
	
	@Override
	public void insertCustIntegralDetail(CustIntegralDetail record){
		baseMapper.insert(record);
	}
	
	@Override
	public void insertAccountIntegralDetail(AccountIntegralDetail record){
		accountIntegralMapper.insert(record);
	}
	
	@Override
	public List<IntegralAdjustAction> selectIntegralAdjustActionByExample(IntegralAdjustAction example){
		return integralAdjusActionMapper.selectList(Condition.getQueryWrapper(example));
	}
	
	@Override
	public void updateByExampleSelective(@Param("record") IntegralAdjustAction record, @Param("example") IntegralAdjustAction example){
		integralAdjusActionMapper.update(record,Condition.getQueryWrapper(example));
	}
	
	@Override
	public void insertIntegralStatusAction(IntegralStatusAction record){
		integralStatusActionMapper.insert(record);
	}
	
	@Override
	public long countByIntegralStatusActionExample(IntegralStatusAction example){
		return integralStatusActionMapper.selectCount(Condition.getQueryWrapper(example));
	}
	
	@Override
	public void updateIntegralStatusActionByExampleSelective(@Param("record") IntegralStatusAction record, @Param("example") IntegralStatusAction example){
		integralStatusActionMapper.update(record,Condition.getQueryWrapper(example));
	}
	
	@Override
	public List<IntegralStatusAction> selectntegralStatusActionByExample(IPage page, Wrapper<IntegralStatusAction> queryWrapper){
		return  integralStatusActionMapper.selectPage(page,queryWrapper).getRecords();
	}
	
	@Override
	public int updateAccountDeatilByExampleSelective(@Param("record") AccountIntegralDetail record, @Param("example") AccountIntegralDetail example){
		return accountIntegralMapper.update(record,Condition.getQueryWrapper(example));
	}

	@Override
	public List<IntegralAdjustAction> selectIntegralAdjustActionPageByExample(IPage<IntegralAdjustAction> page, QueryWrapper<IntegralAdjustAction> queryWrapper) {
		return integralAdjusActionMapper.selectPage(page,queryWrapper).getRecords();
	}

}
