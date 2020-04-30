package org.springclouddev.integral.utils;

import org.springclouddev.integral.entity.AccountIntegralDetail;
import org.springclouddev.integral.entity.CustIntegralDetail;
import org.springclouddev.integral.entity.IntegralStatusAction;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.impl.custIntegral.CustIntegralService;
import org.springclouddev.integral.vo.AccountIntegralVo;
import org.springclouddev.integral.vo.CustIntegralVo;
import org.springclouddev.integral.vo.IntegralStatusActionVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IntegralUtil {
	@Autowired
	private CustIntegralService custIntegralService;
	public static CustIntegralVo formatToCustIntegralVo(CustIntegralDetail custDetail, String blance, String usedBlance){
		CustIntegralVo custInfo = new CustIntegralVo();
		custInfo.setAccountId(custDetail.getAccountId());
		custInfo.setCustId(custDetail.getCustId());
		custInfo.setCustName(custDetail.getCustName());
		custInfo.setPhoneNum(custDetail.getPhoneNum());
		custInfo.setCustAddress(custDetail.getCustAddress());
		custInfo.setIntegralType(custDetail.getIntegralType());
		custInfo.setIntegralTypeId(custDetail.getIntegralTypeId());
		custInfo.setBlance(blance);
		custInfo.setUsedBlance(usedBlance);
		custInfo.setAccountStatusId(custDetail.getAccountStatusId());
		custInfo.setAccountStatus(LocalDictCache.getDicPrmById(custDetail.getAccountStatusId()).getName());
		return custInfo;
	}
	
	public static AccountIntegralVo formatToAccountIntegralInfo(List<AccountIntegralDetail> list, String integralEndMonth ){
		AccountIntegralVo accountInfo = new AccountIntegralVo();
		AccountIntegralDetail accountDetail = list.get(0);
		accountInfo.setAccountId(accountDetail.getAccountId());
		accountInfo.setCustId(accountDetail.getCustId());
		accountInfo.setChangeDate(accountDetail.getChangeDate());
		accountInfo.setChangeIntegral(accountDetail.getChangeIntegral());
		accountInfo.setIntegralValidBlance(accountDetail.getIntegralValidBlance());
		accountInfo.setAccountBlance(accountDetail.getAccountBlance());
		accountInfo.setBusiness(accountDetail.getBusiness());
		accountInfo.setRewark(accountDetail.getRemark());
		accountInfo.setIntegralEndMonth(integralEndMonth);
		return accountInfo;
	}
	
	public static IntegralStatusActionVo formatToIntegralStatusActionVo(IntegralStatusAction integralStatusAction){
		IntegralStatusActionVo IntegralStatusActionVo = new IntegralStatusActionVo();
		IntegralStatusActionVo.setAccountId(integralStatusAction.getAccountId());
		IntegralStatusActionVo.setAccountStatus(integralStatusAction.getAccountStatus());
		IntegralStatusActionVo.setAccountStatusId(integralStatusAction.getAccountStatusId());
		IntegralStatusActionVo.setBlance(integralStatusAction.getBlance());
		IntegralStatusActionVo.setCrtTime(integralStatusAction.getCreateTime().toString());
		IntegralStatusActionVo.setCrtUser(integralStatusAction.getCreateUser().toString());
		IntegralStatusActionVo.setCustId(integralStatusAction.getCustId());
		IntegralStatusActionVo.setCustName(integralStatusAction.getCustName());
		IntegralStatusActionVo.setIntegralType(integralStatusAction.getIntegralType());
		IntegralStatusActionVo.setLstUpdTime(integralStatusAction.getUpdateTime().toString());
		IntegralStatusActionVo.setLstUpdUser(integralStatusAction.getUpdateUser().toString());
		IntegralStatusActionVo.setOperationType(integralStatusAction.getOperationType());
		IntegralStatusActionVo.setRejectReason(integralStatusAction.getRejectReason());
		IntegralStatusActionVo.setReserveColumn1(integralStatusAction.getReserveColumn1());
		IntegralStatusActionVo.setReserveColumn2(integralStatusAction.getReserveColumn2());
		IntegralStatusActionVo.setReserveColumn3(integralStatusAction.getReserveColumn3());
		return IntegralStatusActionVo;
	}
	
}
