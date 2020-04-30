package org.springclouddev.integral.controller.custIntegral;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.integral.entity.AccountIntegralDetail;
import org.springclouddev.integral.entity.CustIntegralDetail;
import org.springclouddev.integral.entity.IntegralAct;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.impl.actIntegral.IntegralActService;
import org.springclouddev.integral.service.impl.custIntegral.CustIntegralService;
import org.springclouddev.integral.utils.DateFormatUtil;
import org.springclouddev.integral.vo.AdjustInfoVo;
import org.springclouddev.integral.vo.AdjustIntegralVo;
import org.springclouddev.integral.vo.CustIntegralVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Api("账户积分调整API")
@RestController
public class IntegralAdjustController {
	private static Logger logger = LoggerFactory.getLogger(IntegralAdjustController.class);
	@Autowired
	private CustIntegralService custIntegralService;
	@Autowired
	private IntegralActService actIntegralService;
	public void adjustIntegral(AdjustInfoVo adVo) throws ParseException{
		String clientId = adVo.getClientId();
		String actId = 	adVo.getActId();	
		String reason = adVo.getReason();
		String adjustNum = adVo.getAdjustNum();
		String integralId = adVo.getIntegralId();
		String accountId = "";//根据clientId，actId联合查询
		List<IntegralAct> actList = actIntegralService.queryByActCode(actId);
		IntegralAct actIntegralInfo = new IntegralAct();
		if(actList!=null && actList.size()>0){
			actIntegralInfo = actList.get(0); 
		}
		String integralValidDateType = actIntegralInfo.getIntegralLimitTimeType();//通过获得积分当月+活动有效期计算得到
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		String integralValidDate = null;
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(time));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if("1".equals(integralValidDateType)){
			//则是永久期限
			integralValidDate="永久";
		}else{
			Long limitYearNum = actIntegralInfo.getIntegralLimitYearNum();
			calendar.add(Calendar.MONTH, Integer.parseInt(String.valueOf(limitYearNum*12)));
			Date date = calendar.getTime();
			integralValidDate = sdf.format(date);
		}
		//入交易明细表
		AccountIntegralDetail accountIntegralDetailExample = new AccountIntegralDetail();
	    accountIntegralDetailExample.setAccountId(accountId);
	    List<AccountIntegralDetail> accountIntegralDetailList = custIntegralService.selectAccountIntegralDetailByExample(accountIntegralDetailExample);

		CustIntegralDetail custIntegralDetailExample =new CustIntegralDetail();
	    custIntegralDetailExample.setAccountId(accountId);
	    List<CustIntegralDetail> custIntegralDetailExampleList = custIntegralService.selectCustIntegralDetailByExample(custIntegralDetailExample);
	  
	    //计算该客户的总余额
		CustIntegralDetail custIntegralDetailExampleTotal =new CustIntegralDetail();
	    custIntegralDetailExampleTotal.setCustId(clientId);
		List<CustIntegralDetail> custIntegralDetailList = new ArrayList<CustIntegralDetail>();
		custIntegralDetailList = custIntegralService.getCustIntegralInfo(custIntegralDetailExampleTotal);
		int blance = 0;//当前总余额
		CustIntegralVo custIntegralVo =new CustIntegralVo();
		if(custIntegralDetailList!=null && custIntegralDetailList.size()>0){
			for(CustIntegralDetail custDetail:custIntegralDetailList){
				blance = blance+Integer.valueOf(custDetail.getIntegralValidBlance());
			}
		}
		
	    String integralValidBlance = "0";//最后一笔积分交易的有效期余额
	    if(accountIntegralDetailList!=null && accountIntegralDetailList.size()>0){
	    	//有交易记录情况 取最后一次交易记录的总额
	    	integralValidBlance = accountIntegralDetailList.get(0).getIntegralValidBlance();
	    }else{
	    	//无交易记录情况 取账户表的余额
	    	integralValidBlance = custIntegralDetailExampleList.get(0).getIntegralValidBlance();
	    }
	    int intAccountBlance = 0;
	    int intIntegralValidBlance = 0;
	    String num = adjustNum.substring(1, adjustNum.length());
	    if(adjustNum.contains("+")){
	    	intIntegralValidBlance = Integer.valueOf(integralValidBlance)+Integer.valueOf(num);
	    	blance = blance+Integer.valueOf(num);
	    }else{
	    	intIntegralValidBlance = Integer.valueOf(integralValidBlance)-Integer.valueOf(num);
	    	blance = blance-Integer.valueOf(num);
	    }
	    AccountIntegralDetail accountIntegralDetail = new AccountIntegralDetail();
	    accountIntegralDetail.setAccountId(accountId);
	    accountIntegralDetail.setCustId(clientId);
	    accountIntegralDetail.setChangeDate(DateFormatUtil.dateToString(new Date()));
	    accountIntegralDetail.setChangeIntegral(adjustNum);
	    accountIntegralDetail.setBusiness(reason);
	    accountIntegralDetail.setRemark(reason);
	    accountIntegralDetail.setIntegralValidDate(integralValidDate);
	    accountIntegralDetail.setAccountBlance(blance+"");
	    accountIntegralDetail.setIntegralType(LocalDictCache.getDicPrmById(integralId).getName());
	    accountIntegralDetail.setIntegralValidBlance(intIntegralValidBlance+"");
	    custIntegralService.insertAccountIntegralDetail(accountIntegralDetail);

		//入账户明细表(没有就新增，有就更新)
		CustIntegralDetail custIntegralDetail = new CustIntegralDetail();
		AccountIntegralDetail accountIntegralDetailExample2 = new AccountIntegralDetail();
		accountIntegralDetailExample2.setAccountId(accountId);
		List<AccountIntegralDetail> accountIntegralDetailList2 = custIntegralService.getAccountIntegralDetail(accountIntegralDetailExample2);
		int usedNum = 0;
		if(accountIntegralDetailList2!=null && accountIntegralDetailList2.size()>0){
			for(AccountIntegralDetail accountIntegralDetail2:accountIntegralDetailList2){
				String adjustNum2 = accountIntegralDetail2.getChangeIntegral();
				usedNum = usedNum+Integer.valueOf(adjustNum2);
			}
		}
		custIntegralDetail.setUsedToatl(Math.abs(usedNum)+"");
		CustIntegralDetail custIntegralDetailExample2 =new CustIntegralDetail();
	    custIntegralDetailExample2.setAccountId(accountId);
	    if(custIntegralDetailExampleList!=null && custIntegralDetailExampleList.size()>0){
	    	custIntegralDetail.setAccountBlance(intAccountBlance+"");
		    custIntegralDetail.setAccountId(accountId);
		    custIntegralDetail.setIntegralValidBlance(intIntegralValidBlance+"");
	    	custIntegralService.updateCustIntegralDetailByExampleSelective(custIntegralDetail, custIntegralDetailExample2);
	    }else{
	    	custIntegralService.insertCustIntegralDetail(custIntegralDetail);
	    }
		
	}
	
	@ApiOperation("账户积分转入转出")
	@PostMapping("/custIntegral/transferBlance")
	@Transactional(rollbackFor=Exception.class)
	public void transferIntegral(@RequestBody AdjustIntegralVo adVo) throws Exception{
		try {
			logger.info("开始处理转出账户");
			String clientId = adVo.getCustId();
			String adjustNum = "-"+adVo.getAdjustNum();
			String integralId = adVo.getIntegralTypeId();
			String accountId = adVo.getAccountId();
			String integralValidDate = adVo.getIntegralValidDate();
			handleTransfer(clientId,adjustNum,integralId,accountId,integralValidDate);
		    logger.info("转出账户处理结束");
		    
		    logger.info("开始处理转入用户");
		    String transferClientId = adVo.getTransferCustId();
		    String transferAdjustNum = "+"+adVo.getAdjustNum();
			String transferIntegralId = adVo.getTransferIntegralType();
			String transferAccountId = adVo.getTransferAccountId();
			//通过custid和accountid查询cust表的integralValidDate
			CustIntegralDetail example = new CustIntegralDetail();
			example.setCustId(transferClientId).setAccountId(transferAccountId);
			List<CustIntegralDetail> list = custIntegralService.getCustIntegralInfo(example);
			String transferIntegralValidDate = list.get(0).getIntegralValidDate();
			handleTransfer(transferClientId,transferAdjustNum,transferIntegralId,transferAccountId,transferIntegralValidDate);
			logger.info("转入用户处理结束");
		} catch (Exception e) {
			logger.error("审核失败"+e.getMessage()+e);
			throw new Exception();
		}
		
		
	}
	
	public void handleTransfer(String clientId,String adjustNum,String integralId,
			String accountId,String integralValidDate) throws ParseException{
		//入交易明细表
		AccountIntegralDetail accountIntegralDetailExample = new AccountIntegralDetail();
	    accountIntegralDetailExample.setAccountId(accountId);
	    List<AccountIntegralDetail> accountIntegralDetailList = custIntegralService.selectAccountIntegralDetailByExample(accountIntegralDetailExample);

		CustIntegralDetail custIntegralDetailExample =new CustIntegralDetail();
	    custIntegralDetailExample.setAccountId(accountId);
	    List<CustIntegralDetail> custIntegralDetailExampleList = custIntegralService.selectCustIntegralDetailByExample(custIntegralDetailExample);
	  
	    //计算该客户的总余额
		CustIntegralDetail custIntegralDetailExampleTotal =new CustIntegralDetail();
	    custIntegralDetailExampleTotal.setCustId(clientId);
		List<CustIntegralDetail> custIntegralDetailList = new ArrayList<CustIntegralDetail>();
		custIntegralDetailList = custIntegralService.getCustIntegralInfo(custIntegralDetailExampleTotal);
		int blance = 0;//当前总余额
		CustIntegralVo  custIntegralVo =new CustIntegralVo();
		if(custIntegralDetailList!=null && custIntegralDetailList.size()>0){
			for(CustIntegralDetail custDetail:custIntegralDetailList){
				blance = blance+Integer.valueOf(custDetail.getIntegralValidBlance());
			}
		}
		
	    String integralValidBlance = "0";//最后一笔积分交易的有效期余额
	    if(accountIntegralDetailList!=null && accountIntegralDetailList.size()>0){
	    	//有交易记录情况 取最后一次交易记录的总额
	    	integralValidBlance = accountIntegralDetailList.get(0).getIntegralValidBlance();
	    }else{
	    	//无交易记录情况 取账户表的余额
	    	integralValidBlance = custIntegralDetailExampleList.get(0).getIntegralValidBlance();
	    }
	    int intIntegralValidBlance = 0;
	    String num = adjustNum.substring(1, adjustNum.length());
	    if(adjustNum.contains("+")){
	    	intIntegralValidBlance = Integer.valueOf(integralValidBlance)+Integer.valueOf(num);
	    	blance = blance+Integer.valueOf(num);
	    }else{
	    	intIntegralValidBlance = Integer.valueOf(integralValidBlance)-Integer.valueOf(num);
	    	blance = blance-Integer.valueOf(num);
	    }
	    AccountIntegralDetail accountIntegralDetail = new AccountIntegralDetail();
	    accountIntegralDetail.setAccountId(accountId);
	    accountIntegralDetail.setCustId(clientId);
	    accountIntegralDetail.setChangeDate(DateFormatUtil.dateToString(new Date()));
	    accountIntegralDetail.setChangeIntegral(adjustNum);
	    accountIntegralDetail.setBusiness("内部转出");
	    accountIntegralDetail.setRemark("内部转出");
	    accountIntegralDetail.setIntegralValidDate(integralValidDate);
	    accountIntegralDetail.setAccountBlance(blance+"");
	    accountIntegralDetail.setIntegralType(LocalDictCache.getDicPrmById(integralId).getName());
	    accountIntegralDetail.setIntegralValidBlance(intIntegralValidBlance+"");
	    custIntegralService.insertAccountIntegralDetail(accountIntegralDetail);
	    
		//入账户明细表(没有就新增，有就更新)
	     CustIntegralDetail custIntegralDetail = new CustIntegralDetail();
		AccountIntegralDetail accountIntegralDetailExample2 = new AccountIntegralDetail();
	    accountIntegralDetailExample2.setAccountId(accountId);
	    List<AccountIntegralDetail> accountIntegralDetailList2 = custIntegralService.getAccountIntegralDetail(accountIntegralDetailExample2);
	    int usedNum = 0;
	    if(accountIntegralDetailList2!=null && accountIntegralDetailList2.size()>0){
	    	for(AccountIntegralDetail accountIntegralDetail2:accountIntegralDetailList2){
	    		String adjustNum2 = accountIntegralDetail2.getChangeIntegral();
	    		usedNum = usedNum+Integer.valueOf(adjustNum2);
	    	}
	    }
	    custIntegralDetail.setUsedToatl(Math.abs(usedNum)+"");
		CustIntegralDetail custIntegralDetailExample2 =new CustIntegralDetail();
	    custIntegralDetailExample2.setAccountId(accountId);
	    if(custIntegralDetailExampleList!=null && custIntegralDetailExampleList.size()>0){
	    	custIntegralDetail.setAccountBlance(blance+"");
		    custIntegralDetail.setAccountId(accountId);
		    custIntegralDetail.setIntegralValidBlance(intIntegralValidBlance+"");
	    	custIntegralService.updateCustIntegralDetailByExampleSelective(custIntegralDetail, custIntegralDetailExample2);
	    }else{
	    	custIntegralService.insertCustIntegralDetail(custIntegralDetail);
	    }
	}
}
