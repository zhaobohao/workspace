package org.springclouddev.integral.controller.custIntegral;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.annotation.PreAuth;
import org.springclouddev.core.secure.utils.SecureUtil;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.entity.AccountIntegralDetail;
import org.springclouddev.integral.entity.CustIntegralDetail;
import org.springclouddev.integral.entity.IntegralAdjustAction;
import org.springclouddev.integral.entity.IntegralStatusAction;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.accountintegral.IaccountIntegralDetailService;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.custIntegral.ICustIntegralService;
import org.springclouddev.integral.utils.DateFormatUtil;
import org.springclouddev.integral.utils.IntegralUtil;
import org.springclouddev.integral.vo.AccountPostPoneVo;
import org.springclouddev.integral.vo.AdjustIntegralVo;
import org.springclouddev.integral.vo.CustIntegralVo;
import org.springclouddev.integral.vo.IntegralStatusActionVo;
import org.springclouddev.integral.wrapper.AdjustIntegralWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

@Api("账户积分相关API")
@RestController
public class CustIntegralController {
    private static Logger logger = LoggerFactory.getLogger(CustIntegralController.class);


    @Autowired
    private ICustIntegralService custIntegralService;
    @Autowired
    private IaccountIntegralDetailService accountIntegralDetailService;

    @ApiOperation("账户积分明细页面")
    @GetMapping("/custIntegral/custDetailList")
    @PreAuth("admin:integralBalance:query")
    public Object getCustIntegralPageList(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int limit,
                                          @RequestParam Map<String, Object> custIntegralDetail) throws ParseException {

        Query query = new Query();
        query.setSize(limit);
        query.setCurrent(page);
        IPage<CustIntegralDetail> pages = custIntegralService.page(Condition.getPage(query), Condition.getQueryWrapper(custIntegralDetail, CustIntegralDetail.class));

        for (CustIntegralDetail custDeatil : pages.getRecords()) {
            custDeatil.setIntegralType(LocalDictCache.getDicPrmById(custDeatil.getIntegralTypeId()).getName());
        }
        return R.data(pages);
    }

    @ApiOperation("积分交易明细页面")
    @GetMapping("/custIntegral/AccountList")
    @PreAuth("admin:tradeDetailPage:query")
    public Object getAccountListDetail(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String identyCard,
                                       @RequestParam(required = false) String identyType,
                                       @RequestParam Map<String, Object> accountIntegralDetailMap) {
        try {
            Query query = new Query();
            query.setSize(limit);
            query.setCurrent(page);
            query.setDescs("id");
            IPage<AccountIntegralDetail> pages = accountIntegralDetailService.selectPageByJoinCustIntegralDetail(Condition.getPage(query), Condition.getQueryWrapper(accountIntegralDetailMap, AccountIntegralDetail.class), identyType, identyCard);

            return R.data(pages);
        } catch (Exception e) {
            logger.info("查询积分明细失败：" + e + e.getMessage());
            return R.fail(ReturnCode.SYSTEM_EXCEPTION);
        }
    }

    @ApiOperation("客户账户总明细记录")
    @GetMapping("/custIntegral/totalAccount")
    @PreAuth("admin:integralBalance:query")
    public Object getTotalCustIntegralPageList(@RequestParam(required = false) String custId,
                                               @RequestParam(required = false) String identyType,
                                               @RequestParam(required = false) String identyCard, @RequestParam Map<String, Object> accountIntegralDetailMap) {
        if (StringUtils.isBlank(custId) && StringUtils.isBlank(identyCard)) {
            return R.data(new ArrayList());
        }
        //TODO:算法需要优化
        List<CustIntegralVo> custIntegralVoList = new ArrayList<CustIntegralVo>();
        List<CustIntegralDetail> custIntegralDetailList = custIntegralService.list(Condition.getQueryWrapper(accountIntegralDetailMap, CustIntegralDetail.class));
        int blance = 0;//当前余额
        int usedBlance = 0;//已使用余额
        CustIntegralVo custIntegralVo = new CustIntegralVo();
        if (custIntegralDetailList != null && custIntegralDetailList.size() > 0) {
            for (CustIntegralDetail custDetail : custIntegralDetailList) {
                blance = blance + Integer.valueOf(custDetail.getIntegralValidBlance());
                usedBlance = usedBlance + Integer.valueOf(custDetail.getUsedToatl());
            }
            custIntegralVo = IntegralUtil.formatToCustIntegralVo(custIntegralDetailList.get(0), blance + "", usedBlance + "");
            String integralName = LocalDictCache.getDicPrmById(custIntegralDetailList.get(0).getIntegralTypeId()).getName();
            custIntegralVo.setIntegralType(integralName);

            custIntegralVoList.add(custIntegralVo);
        }
        return R.data(custIntegralVoList);
    }


    @ApiOperation("客户账户积分调整页面")
    @GetMapping("/custIntegral/adjustAccountList")
    @PreAuth("admin:integralAdjust:query")
    public Object getIntegralAdjustList(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int limit,
                                        @RequestParam(required = false) String custId,
                                        @RequestParam(required = false) String identyType,
                                        @RequestParam(required = false) String identyCard,
                                        @RequestParam Map<String, Object> CustIntegralDetailMap) throws ParseException {
        Query query = new Query();
        query.setSize(limit);
        query.setCurrent(page);
        query.setDescs("id");

        if (StringUtils.isBlank(custId) && StringUtils.isBlank(identyCard)) {
            return R.data(new ArrayList());
        }
        IPage<CustIntegralDetail> pageList = custIntegralService.page(Condition.getPage(query), Condition.getQueryWrapper(CustIntegralDetailMap, CustIntegralDetail.class));
        for (CustIntegralDetail custDeatil : pageList.getRecords()) {
            String integralName = LocalDictCache.getDicPrmById(custDeatil.getIntegralTypeId()).getName();
            custDeatil.setIntegralType(integralName);
            custDeatil.setAccountStatus(LocalDictCache.getDicPrmById(custDeatil.getAccountStatusId()).getName());
        }
        return R.data(pageList);

    }

    @ApiOperation("客户账户积分调整动作页面")
    @PostMapping("/custIntegral/adjustAccountPage")
    @PreAuth("admin:integralAdjust:adjust")
    public Object adjustIntegralPage(@RequestBody String accountId) throws ParseException {
        CustIntegralDetail example = new CustIntegralDetail();
        example.setAccountId(accountId);
        CustIntegralDetail custIntegralDetail = custIntegralService.getOne(Condition.getQueryWrapper(example));
        List<CustIntegralDetail> custIntegralDetailList2 = new ArrayList<CustIntegralDetail>();
        if (custIntegralDetail != null) {
            String integralName = LocalDictCache.getDicPrmById(custIntegralDetail.getIntegralType()).getName();
            custIntegralDetail.setIntegralType(integralName);
        }
        custIntegralDetailList2.add(custIntegralDetail);
        return R.data(custIntegralDetailList2);
    }

    @ApiOperation("客户账户积分调整")
    @PostMapping("/custIntegral/adjustAccount")
    public Object adjustIntegral(@RequestBody AdjustIntegralVo adjustIntegralVo) throws ParseException {
        //accountId必传
        String accountId = adjustIntegralVo.getAccountId();
        String custId = adjustIntegralVo.getCustId();

        String adjustNum = adjustIntegralVo.getAdjustType() + adjustIntegralVo.getAdjustNum();

        String status = "audit_status_wait";//待审核


        //入积分调整动作表
        IntegralAdjustAction integralAction = new IntegralAdjustAction();
        integralAction.setAccountId(accountId);
        integralAction.setAdjustDate(DateFormatUtil.dateToString(new Date()));
        integralAction.setAdjustNum(adjustNum);
        integralAction.setCustId(custId);
        integralAction.setStatusName(status);
        integralAction.setCrtUser(SecureUtil.getUserName());
        integralAction.setAdjustDate((DateFormatUtil.formatDate2(new Date())));
        integralAction.setAdjustReason(adjustIntegralVo.getAdjustReason());
        custIntegralService.insertIntegralAdjustAction(integralAction);

        //入积分交易明细表

//	    AccountIntegralDetailExample accountIntegralDetailExample = new AccountIntegralDetailExample();
//	    accountIntegralDetailExample.createCriteria().andAccountIdEqualTo(accountId);
//	    accountIntegralDetailExample.setOrderByClause("id desc");
//	    List<AccountIntegralDetail> accountIntegralDetailList = custIntegralService.selectAccountIntegralDetailByExample(accountIntegralDetailExample);
//	    String accountBlance = null;//最后一笔积分交易的总账户余额
//	    if(accountIntegralDetailList!=null && accountIntegralDetailList.size()>0){
//	    	accountBlance = accountIntegralDetailList.get(0).getAccountBlance();
//	    }
//	    int intAccountBlance = 0;
//	    int integralValidBlance = 0;
//	    if("+".equals(adjustIntegralVo.getAdjustType())){
//	    	intAccountBlance = Integer.valueOf(accountBlance)+Integer.valueOf(adjustIntegralVo.getAdjustNum());
//	    	integralValidBlance = Integer.valueOf(adjustIntegralVo.getIntegralValidBlance())+Integer.valueOf(adjustIntegralVo.getAdjustNum());
//	    }else{
//	    	intAccountBlance = Integer.valueOf(accountBlance)-Integer.valueOf(adjustIntegralVo.getAdjustNum());
//	    	integralValidBlance = Integer.valueOf(adjustIntegralVo.getIntegralValidBlance())-Integer.valueOf(adjustIntegralVo.getAdjustNum());
//	    }
//	    AccountIntegralDetail accountIntegralDetail = new AccountIntegralDetail();
//	    accountIntegralDetail.setAccountId(accountId);
//	    accountIntegralDetail.setCustId(custId);
//	    accountIntegralDetail.setChangeDate(DateFormatUtil.dateToString(new Date()));
//	    accountIntegralDetail.setChangeIntegral(adjustNum);
//	    accountIntegralDetail.setBusiness("内管调整");
//	    accountIntegralDetail.setRewark("内管调整");
//	    accountIntegralDetail.setIntegralValidDate(adjustIntegralVo.getIntegralValidDate());
////	    accountIntegralDetail.setIdentyCard(identyCard);
////	    accountIntegralDetail.setIdentyType(identyType);
//	    accountIntegralDetail.setAccountBlance(intAccountBlance+"");
//	    accountIntegralDetail.setIntegralType(adjustIntegralVo.getIntegralType());
//	    accountIntegralDetail.setIntegralValidBlance(integralValidBlance+"");
//	    custIntegralService.insertAccountIntegralDetail(accountIntegralDetail);
//		//入账户明细表(没有就新增，有就更新)
//	    CustIntegralDetailExample custIntegralDetailExample =new CustIntegralDetailExample();
//	    List<CustIntegralDetail> custIntegralDetailExampleList = custIntegralService.selectCustIntegralDetailByExample(custIntegralDetailExample);
//	    CustIntegralDetail custIntegralDetail = new CustIntegralDetail();
//	    custIntegralDetail.setAccountBlance(intAccountBlance+"");
//	    custIntegralDetail.setAccountId(accountId);
//	    custIntegralDetail.setAccountStatus("正常");
//	    custIntegralDetail.setCustAddress("");
//	    custIntegralDetail.setCustId(custId);
//	    custIntegralDetail.setCustName(adjustIntegralVo.getCustName());
////	    custIntegralDetail.setIdentyCard(identyCard);
////	    custIntegralDetail.setIdentyType(identyType);
//	    custIntegralDetail.setIntegralType(adjustIntegralVo.getIntegralType());
//	    custIntegralDetail.setIntegralValidBlance(integralValidBlance+"");
//	    custIntegralDetail.setIntegralValidDate(adjustIntegralVo.getIntegralValidDate());
//	    custIntegralDetail.setPhoneNum("");
//	    AccountIntegralDetailExample accountIntegralDetailExample2 = new AccountIntegralDetailExample();
//	    accountIntegralDetailExample2.createCriteria().andChangeIntegralLike("%-%");
//	    List<AccountIntegralDetail> accountIntegralDetailList2 = custIntegralService.getAccountIntegralDetail(accountIntegralDetailExample2);
//	    int usedNum = 0;
//	    if(accountIntegralDetailList2!=null && accountIntegralDetailList2.size()>0){
//	    	for(AccountIntegralDetail accountIntegralDetail2:accountIntegralDetailList2){
//	    		String adjustNum2 = accountIntegralDetail2.getChangeIntegral();
//	    		usedNum = usedNum+Integer.valueOf(adjustNum2);
//	    	}
//	    }
//	    custIntegralDetail.setUsedToatl(usedNum+"");
//	    CustIntegralDetailExample custIntegralDetailExample2 =new CustIntegralDetailExample();
//	    
//	    if(custIntegralDetailExampleList!=null && custIntegralDetailExampleList.size()>0){
//	    	custIntegralService.updateCustIntegralDetailByExampleSelective(custIntegralDetail, custIntegralDetailExample2);
//	    }else{
//	    	custIntegralService.insertCustIntegralDetail(custIntegralDetail);
//	    }

        return R.success("操作成功");
    }

    @ApiOperation("客户账户积分调整详细页面")
    @GetMapping("/custIntegral/adjustAccountDetailPage")
    @PreAuth("admin:integralAdjust:detail")
    public Object adjustIntegralDetail(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String accountId,
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = false) String integralType,
                                       @RequestParam Map<String, Object> custIntegralDetailMap) throws ParseException {
        Query query = new Query();
        query.setSize(limit);
        query.setCurrent(page);
        query.setDescs("id");

        List<CustIntegralDetail> custIntegralDetailList = custIntegralService.getCustIntegralInfo(new CustIntegralDetail().setAccountId(accountId));
        List<IntegralAdjustAction> integralAdjustActionList = custIntegralService.selectIntegralAdjustActionPageByExample(Condition.getPage(query), Condition.getQueryWrapper(custIntegralDetailMap, IntegralAdjustAction.class));
        //最终返回list
        List<AdjustIntegralVo> list = new ArrayList<AdjustIntegralVo>();
        IPage<AdjustIntegralVo> page1 = new Page<AdjustIntegralVo>();
        page1.setCurrent(page);
        page1.setSize(limit);
        if (integralAdjustActionList != null && integralAdjustActionList.size() > 0) {
            for (IntegralAdjustAction integralAdjustAction : integralAdjustActionList) {
                list.add( AdjustIntegralWrapper.build().customWrapperVo(integralAdjustAction,accountId,custIntegralDetailList.get(0)));
            }
            page1.setRecords(list);
            page1.setTotal(list.size());
        }

        return R.data(page1);
    }

    @ApiOperation("客户账户积分调整审核页面")
    @GetMapping("/custIntegral/auditAdjustAccountDetailPage")
    @PreAuth("admin:adjustAudit:query")
    public Object auditAdjustIntegralPage(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int limit,
                                          @RequestParam(required = false) String custId,
                                          @RequestParam(required = false) String status,
                                          @RequestParam(required = false) String identyType,
                                          @RequestParam(required = false) String identyCard,
                                          @RequestParam Map<String, Object> auditAdjustIntegralMap) {
        Query query = new Query();
        query.setSize(limit);
        query.setCurrent(page);
        query.setDescs("id");
        auditAdjustIntegralMap.remove("identyCard");
        List<IntegralAdjustAction> integralAdjustActionList = custIntegralService.selectIntegralAdjustActionPageByExample(Condition.getPage(query),Condition.getQueryWrapper(auditAdjustIntegralMap,IntegralAdjustAction.class));
        Page<AdjustIntegralVo> page1 = new Page<AdjustIntegralVo>();
        page1.setCurrent(page);
        page1.setSize(limit);
		List<AdjustIntegralVo> list = new ArrayList<AdjustIntegralVo>();
        if (integralAdjustActionList != null && integralAdjustActionList.size() > 0) {
            for (IntegralAdjustAction integralAdjustAction : integralAdjustActionList) {
                String accountId = integralAdjustAction.getAccountId();
                List<CustIntegralDetail> custIntegralDetailList = custIntegralService.getCustIntegralInfo(new CustIntegralDetail().setAccountId(accountId));
                AdjustIntegralVo  adjustIntegralVo= AdjustIntegralWrapper.build().customWrapperVo(integralAdjustAction,accountId,custIntegralDetailList.get(0));
                List<String> identyList = getCardNo(accountId);
                if (StringUtils.isNotBlank(identyType) && StringUtils.isNotBlank(identyCard)) {
                    if (identyCard.equals(identyList.get(0)) && identyType.equals(identyList.get(1))) {
                        list.add(adjustIntegralVo);
                    }
                } else {
                    list.add(adjustIntegralVo);
                }
            }
        }
        page1.setRecords(list);
        page1.setTotal(list.size());
        return R.data(page1);
    }


    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("客户账户积分调整审核")
    @PostMapping("/custIntegral/auditAdjustAccountDetail")
    @PreAuth("admin:adjustAudit:audit")
    public Object auditAdjustIntegral(@RequestBody AdjustIntegralVo adjustIntegralVo) throws Exception {

        Long  id = Long.valueOf(adjustIntegralVo.getId());
        try {
            IntegralAdjustAction integralAdjustActionExample = new IntegralAdjustAction();
            integralAdjustActionExample.setId(id);
            IntegralAdjustAction integralAdjustAction1 = new IntegralAdjustAction();
            integralAdjustAction1.setId(id);
            integralAdjustAction1.setStatus(Integer.valueOf(adjustIntegralVo.getStatus()));
            integralAdjustAction1.setLstUpdUser(SecureUtil.getUserId().toString());
            integralAdjustAction1.setLstUpdTime(DateFormatUtil.formatDate2(new Date()));
            //更新调整动作表
            custIntegralService.updateByExampleSelective(integralAdjustAction1, integralAdjustActionExample);

            AccountIntegralDetail accountIntegralDetailExample = new AccountIntegralDetail();
            accountIntegralDetailExample.setAccountId(adjustIntegralVo.getAccountId());
            List<AccountIntegralDetail> accountIntegralDetailList = custIntegralService.selectAccountIntegralDetailByExample(accountIntegralDetailExample);

            CustIntegralDetail custIntegralDetailExample = new CustIntegralDetail();
            custIntegralDetailExample.setAccountId(adjustIntegralVo.getAccountId());
            List<CustIntegralDetail> custIntegralDetailExampleList = custIntegralService.selectCustIntegralDetailByExample(custIntegralDetailExample);

            //计算该客户的总余额
            CustIntegralDetail custIntegralDetailExampleTotal = new CustIntegralDetail();
            custIntegralDetailExampleTotal.setCustId(adjustIntegralVo.getCustId());
            List<CustIntegralDetail> custIntegralDetailList = custIntegralService.getCustIntegralInfo(custIntegralDetailExampleTotal);
            int blance = 0;//当前总余额
            CustIntegralVo custIntegralVo = new CustIntegralVo();
            if (custIntegralDetailList != null && custIntegralDetailList.size() > 0) {
                for (CustIntegralDetail custDetail : custIntegralDetailList) {
                    blance = blance + Integer.valueOf(custDetail.getIntegralValidBlance());
                }
            }

            String integralValidBlance = "0";//最后一笔积分交易的有效期余额
            if (accountIntegralDetailList != null && accountIntegralDetailList.size() > 0) {
                //有交易记录情况 取最后一次交易记录的总额
                integralValidBlance = accountIntegralDetailList.get(0).getIntegralValidBlance();
            } else {
                //无交易记录情况 取账户表的余额
                integralValidBlance = custIntegralDetailExampleList.get(0).getIntegralValidBlance();
            }
            int intIntegralValidBlance = 0;
            String num = adjustIntegralVo.getAdjustNum().substring(1, adjustIntegralVo.getAdjustNum().length());
            if (adjustIntegralVo.getAdjustNum().contains("+")) {
                intIntegralValidBlance = Integer.valueOf(integralValidBlance) + Integer.valueOf(num);
                blance = blance + Integer.valueOf(num);
            } else {
                intIntegralValidBlance = Integer.valueOf(integralValidBlance) - Integer.valueOf(num);
                blance = blance - Integer.valueOf(num);
            }
            AccountIntegralDetail accountIntegralDetail = new AccountIntegralDetail();
            accountIntegralDetail.setAccountId(adjustIntegralVo.getAccountId());
            accountIntegralDetail.setCustId(adjustIntegralVo.getCustId());
            accountIntegralDetail.setChangeDate(DateFormatUtil.dateToString(new Date()));
            accountIntegralDetail.setChangeIntegral(adjustIntegralVo.getAdjustNum());
            accountIntegralDetail.setBusiness(adjustIntegralVo.getAdjustReason());
            accountIntegralDetail.setRemark(adjustIntegralVo.getAdjustReason());
            accountIntegralDetail.setIntegralValidDate(adjustIntegralVo.getIntegralValidDate());
            accountIntegralDetail.setAccountBlance(blance + "");
            accountIntegralDetail.setIntegralType(adjustIntegralVo.getIntegralType());
            accountIntegralDetail.setIntegralValidBlance(intIntegralValidBlance + "");
            custIntegralService.insertAccountIntegralDetail(accountIntegralDetail);

            //入账户明细表(没有就新增，有就更新)
            CustIntegralDetail custIntegralDetail = new CustIntegralDetail();
            AccountIntegralDetail accountIntegralDetailExample2 = new AccountIntegralDetail();
            accountIntegralDetailExample2.setAccountId(adjustIntegralVo.getAccountId());
            List<AccountIntegralDetail> accountIntegralDetailList2 = accountIntegralDetailService.list(Condition.getQueryWrapper(accountIntegralDetailExample2).lambda().like(AccountIntegralDetail::getChangeIntegral,"-"));
            int usedNum = 0;
            if (accountIntegralDetailList2 != null && accountIntegralDetailList2.size() > 0) {
                for (AccountIntegralDetail accountIntegralDetail2 : accountIntegralDetailList2) {
                    String adjustNum2 = accountIntegralDetail2.getChangeIntegral();
                    usedNum = usedNum + Integer.valueOf(adjustNum2);
                }
            }
            custIntegralDetail.setUsedToatl(Math.abs(usedNum) + "");
            CustIntegralDetail custIntegralDetailExample2 = new CustIntegralDetail();
            custIntegralDetailExample2.setAccountId(adjustIntegralVo.getAccountId());
            if (custIntegralDetailExampleList != null && custIntegralDetailExampleList.size() > 0) {
                custIntegralDetail.setAccountBlance(blance + "");
                custIntegralDetail.setAccountId(adjustIntegralVo.getAccountId());
                custIntegralDetail.setIntegralValidBlance(intIntegralValidBlance + "");
                custIntegralService.updateCustIntegralDetailByExampleSelective(custIntegralDetail, custIntegralDetailExample2);
            } else {
                custIntegralService.insertCustIntegralDetail(custIntegralDetail);
            }
        } catch (Exception e) {
            logger.error("审核失败" + e.getMessage() + e);
            throw new Exception();
        }


        return R.success("操作成功");
    }

    @ApiOperation("积分冻结解冻页面")
    @GetMapping("/custIntegral/frozenStatusPage")
    @PreAuth("admin:freeze:query")
    public Object getfrozenStatusPageList(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int limit,
                                          @RequestParam(required = false) String custId,
                                          @RequestParam(required = false) String identyType,
                                          @RequestParam(required = false) String identyCard,
                                          Map<String, Object> custIntegralDetailExample) {
        Query query = new Query();
        query.setSize(limit);
        query.setCurrent(page);
        query.setDescs("id");
        if (StringUtils.isBlank(custId) && StringUtils.isBlank(identyCard)) {
            return R.data(new ArrayList<>());
        }
        Page<CustIntegralVo> page1 = new Page<CustIntegralVo>();
        List<CustIntegralDetail>  custIntegralDetailList = custIntegralService.page(Condition.getPage(query),Condition.getQueryWrapper(custIntegralDetailExample,CustIntegralDetail.class)).getRecords();
        List<CustIntegralVo> list =new LinkedList<>();
//		int blance = 0;//当前余额
//		int usedBlance = 0;//已使用余额
        for (CustIntegralDetail custDetail : custIntegralDetailList) {
//			blance = blance+Integer.valueOf(custDetail.getAccountBlance());
//			usedBlance = usedBlance+Integer.valueOf(custDetail.getUsedToatl());
            String blance = custDetail.getIntegralValidBlance();
            String usedBlance = custDetail.getUsedToatl();
            CustIntegralVo custIntegralVo = IntegralUtil.formatToCustIntegralVo(custDetail, blance, usedBlance);
            list.add(custIntegralVo);
        }
        page1.setRecords(list);
        page1.setTotal(((Page) custIntegralDetailList).getTotal());
        return R.data(page1);
    }

    @ApiOperation("积分冻结解")
    @PostMapping("/custIntegral/frozenStatus")
    @PreAuth("admin:freeze:freeze")
    public Object getfrozenStatusDetaiPageList(@RequestBody CustIntegralVo custIntegralVo) throws ParseException {
        CustIntegralDetail custIntegralDetailExample = new CustIntegralDetail();
        custIntegralDetailExample.setAccountId(custIntegralVo.getAccountId());
        CustIntegralDetail custIntegralDetail = new CustIntegralDetail();
        custIntegralDetail.setAccountId(custIntegralVo.getAccountId());
        custIntegralDetail.setAccountStatusId(custIntegralVo.getAccountStatusId());
        custIntegralService.updateCustIntegralDetailByExampleSelective(custIntegralDetail, custIntegralDetailExample);
        IntegralStatusAction integralStatusAction = new IntegralStatusAction();
        IntegralStatusAction example = new IntegralStatusAction();
        example.setAccountId(custIntegralVo.getAccountId());
//		if(custIntegralService.countByIntegralStatusActionExample(example)>0){
//			integralStatusAction.setAccountId(custIntegralVo.getAccountId());
//			integralStatusAction.setBlance(custIntegralVo.getBlance());
//			integralStatusAction.setCustId(custIntegralVo.getCustId());
//			integralStatusAction.setCustName(custIntegralVo.getCustName());
//			integralStatusAction.setIntegralType(custIntegralVo.getIntegralTypeId());
//			
//			integralStatusAction.setCrtTime(DateFormatUtil.dateToString(new Date()));
//			integralStatusAction.setCrtUser(((AdminUser)SecurityUtils.getSubject().getPrincipal()).getUserId());
//			integralStatusAction.setAccountStatusId(custIntegralVo.getAccountStatusId());
//			if("2".equals(custIntegralVo.getAccountStatusId())){
//				integralStatusAction.setOperationType("冻结");
//			}else if("3".equals(custIntegralVo.getAccountStatusId())){
//				integralStatusAction.setOperationType("解冻");
//			}
//			custIntegralService.updateIntegralStatusActionByExampleSelective(integralStatusAction, example);
//		}else{
        integralStatusAction.setAccountId(custIntegralVo.getAccountId());
        integralStatusAction.setBlance(custIntegralVo.getBlance());
        integralStatusAction.setCreateTime(LocalDateTime.now());
        integralStatusAction.setCreateUser(SecureUtil.getUserId());
        integralStatusAction.setCustId(custIntegralVo.getCustId());
        integralStatusAction.setCustName(custIntegralVo.getCustName());
        integralStatusAction.setIntegralType(custIntegralVo.getIntegralTypeId());
        integralStatusAction.setAccountStatusId(custIntegralVo.getAccountStatusId());
        if ("2".equals(custIntegralVo.getAccountStatusId())) {
            integralStatusAction.setOperationType("冻结");
        } else if ("3".equals(custIntegralVo.getAccountStatusId())) {
            integralStatusAction.setOperationType("解冻");
        }
        custIntegralService.insertIntegralStatusAction(integralStatusAction);
//		}
        Map<String, Object> data = Maps.newHashMap();
        data.put("accountStatusId", custIntegralVo.getAccountStatusId());
        data.put("accountStatus", LocalDictCache.getDicPrmById(custIntegralVo.getAccountStatusId()).getName());
        return R.data(data);
    }

    @ApiOperation("积分冻结解冻详细页面")
    @GetMapping("/custIntegral/frozenStatusDetaiPage")
    @PreAuth("admin:freeze:detail")
    public Object getfrozenStatusDetaiPageList(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam(required = false) String accountId,
                                               @RequestParam(required = false) String custId,
                                               @RequestParam(required = false) String identyType,
                                               @RequestParam(required = false) String identyCard,
     Map<String, Object> integralStatusActionExample) {
        Query query = new Query();
        query.setSize(limit);
        query.setCurrent(page);
        query.setDescs("id");

        List<IntegralStatusAction> list = new ArrayList<IntegralStatusAction>();
        List<IntegralStatusActionVo> list2 = new ArrayList<IntegralStatusActionVo>();
        Page<IntegralStatusActionVo> page1 = new Page<IntegralStatusActionVo>();
        list = custIntegralService.selectntegralStatusActionByExample(Condition.getPage(query),Condition.getQueryWrapper(integralStatusActionExample,IntegralStatusAction.class));
        if (list != null && list.size() > 0) {
            for (IntegralStatusAction integralStatusAction : list) {
                integralStatusAction.setAccountStatus(LocalDictCache.getDicPrmById(integralStatusAction.getAccountStatusId()).getName());
                IntegralStatusActionVo integralStatusActionVo = new IntegralStatusActionVo();
                integralStatusActionVo = IntegralUtil.formatToIntegralStatusActionVo(integralStatusAction);
                integralStatusActionVo.setIntegralTypeName(LocalDictCache.getDicPrmById(integralStatusActionVo.getIntegralType()).getName());
                List<String> identyList = getCardNo(integralStatusActionVo.getAccountId());
                if (StringUtils.isNotBlank(identyType) && StringUtils.isNotBlank(identyCard)) {
                    if (identyCard.equals(identyList.get(0)) && identyType.equals(identyList.get(1))) {
                        list2.add(integralStatusActionVo);
                    }
                } else {
                    list2.add(integralStatusActionVo);
                }
            }
            page1.setRecords(list2);
            page1.setTotal(((Page) list).getTotal());
        }
        return R.data(page1);
    }

    @ApiOperation("积分冻结解冻审核页面")
    @GetMapping("/custIntegral/AuditFrozenPage")
    @PreAuth("admin:freeAudit:query")
    public Object getAuditFrozenPageList(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int limit,
                                         @RequestParam(required = false) String custId,
                                         @RequestParam(required = false) String identyType,
                                         @RequestParam(required = false) String identyCard,
     Map<String, Object> integralStatusActionExample) {
        Query query = new Query();
        query.setSize(limit);
        query.setCurrent(page);
        query.setDescs("id");

        List<IntegralStatusAction> list = new ArrayList<IntegralStatusAction>();
        list = custIntegralService.selectntegralStatusActionByExample(Condition.getPage(query),Condition.getQueryWrapper(integralStatusActionExample,IntegralStatusAction.class).lambda().in(IntegralStatusAction::getStatus,"2","3"));
        List<IntegralStatusAction> list2 = new ArrayList<IntegralStatusAction>();
        Page<IntegralStatusAction> page1 = new Page<IntegralStatusAction>();
        if (list != null && list.size() > 0) {
            for (IntegralStatusAction integralStatusAction : list) {
                integralStatusAction.setAccountStatus(LocalDictCache.getDicPrmById(integralStatusAction.getAccountStatusId()).getName());
                List<String> identyList = getCardNo(integralStatusAction.getAccountId());
                if (StringUtils.isNotBlank(identyType) && StringUtils.isNotBlank(identyCard)) {
                    if (identyCard.equals(identyList.get(0)) && identyType.equals(identyList.get(1))) {
                        list2.add(integralStatusAction);
                    }
                } else {
                    list2.add(integralStatusAction);
                }
            }
            page1.setRecords(list2);
            page1.setTotal(((Page) list).getTotal());
        }
        return R.data(page1);
    }

    @ApiOperation("积分冻结解冻审核")
        @PostMapping("/custIntegral/AuditFrozen")
        @PreAuth("admin:freeAudit:audit")
        public Object getAuditFrozen(@RequestBody IntegralStatusAction integralStatusAction) throws ParseException {
        IntegralStatusAction integralStatusActionExample = new IntegralStatusAction();
        integralStatusActionExample.setAccountId(integralStatusAction.getAccountId());

        CustIntegralDetail custIntegralDetailExample = new CustIntegralDetail();
        custIntegralDetailExample.setAccountId(integralStatusAction.getAccountId());
        CustIntegralDetail custIntegralDetail = new CustIntegralDetail();
        custIntegralDetail.setAccountId(integralStatusAction.getAccountId());

        IntegralStatusAction integralStatusAction1 = new IntegralStatusAction();
        integralStatusAction1.setUpdateTime(LocalDateTime.now());
        integralStatusAction1.setUpdateUser(SecureUtil.getUserId());
        if ("audit_status_pass".equals(integralStatusAction.getReserveColumn1())) {
            if ("2".equals(integralStatusAction.getAccountStatusId())) {
                //冻结待审核通过
                integralStatusAction1.setAccountStatusId("0");//变成冻结状态

                custIntegralDetail.setAccountStatusId("0");
            } else if ("3".equals(integralStatusAction.getAccountStatusId())) {
                //解冻待审核通过
                integralStatusAction1.setAccountStatusId("1");//变成正常状态
                custIntegralDetail.setAccountStatusId("1");
            }
        } else if ("audit_status_nopass".equals(integralStatusAction.getReserveColumn1())) {
            if ("2".equals(integralStatusAction.getAccountStatusId())) {
                //冻结待审核不通过
                integralStatusAction1.setAccountStatusId("1");//变成正常状态
                custIntegralDetail.setAccountStatusId("1");
            } else if ("3".equals(integralStatusAction.getAccountStatusId())) {
                //解冻待审核不通过
                integralStatusAction1.setAccountStatusId("0");//变成冻结状态
                custIntegralDetail.setAccountStatusId("0");
            }
        }
        integralStatusAction1.setAccountId(integralStatusAction.getAccountId());
        //更新状态动作表
        custIntegralService.updateIntegralStatusActionByExampleSelective(integralStatusAction1, integralStatusActionExample);
        //更新账户表
        custIntegralService.updateCustIntegralDetailByExampleSelective(custIntegralDetail, custIntegralDetailExample);

        return R.success("操作成功");
    }


    public List<String> getCardNo(String accountId) {
        CustIntegralDetail example = new CustIntegralDetail();
        example.setAccountId(accountId);
        List<CustIntegralDetail> list = custIntegralService.getCustIntegralInfo(example);
        List<String> identyList = new ArrayList<String>();
        if (list != null && list.size() > 0) {
            String identyCrad = list.get(0).getIdentyCard();
            String identyType = list.get(0).getIdentyType();
            identyList.add(identyCrad);
            identyList.add(identyType);
        }
        return identyList;
    }

    @ApiOperation("积分延期")
    @PostMapping("/custIntegral/postPone")
    public Object postPoneValidDate(@RequestBody AccountPostPoneVo vo) {
        String custId = vo.getCustId();
        String accountId = vo.getAccountId();
        String integralValidTime = vo.getIntegralValidDate();
        //根据客户号和账户号更新积分有效期，进行延期。
        CustIntegralDetail custIntegralDetailExample = new CustIntegralDetail();
        custIntegralDetailExample.setAccountId(accountId).setCustId(custId);
        CustIntegralDetail custDetail = new CustIntegralDetail();
        custDetail.setAccountId(accountId);
        custDetail.setCustId(custId);
        custDetail.setIntegralValidDate(integralValidTime);
        custIntegralService.updateCustIntegralDetailByExampleSelective(custDetail, custIntegralDetailExample);
        return R.success("操作成功");
    }

    @ApiOperation("获取转入账户")
    @PostMapping("/custIntegral/getAccountIds")
    public Object getAccountIds(@RequestBody AdjustIntegralVo vo) {
        String custId = vo.getTransferCustId();
        String integralType = vo.getTransferIntegralType();
        CustIntegralDetail custIntegralDetailExample = new CustIntegralDetail();
        custIntegralDetailExample.setCustId(custId).setIntegralType(integralType);
        List<String> accountIdList = new ArrayList<String>();
        List<CustIntegralDetail> custIntegralDetailList = custIntegralService.getCustIntegralInfo(custIntegralDetailExample);
        if (custIntegralDetailList != null && custIntegralDetailList.size() > 0) {
            for (CustIntegralDetail custDetail : custIntegralDetailList) {
                accountIdList.add(custDetail.getAccountId());
            }
        }
        return R.data(accountIdList);
    }


    @ApiOperation("获取账户余额")
    @PostMapping("/custIntegral/getAccountBlance")
    public Object getAccountBlance(@RequestBody AdjustIntegralVo vo) {
        String accountId = vo.getTransferAccountId();
        CustIntegralDetail custIntegralDetailExample = new CustIntegralDetail();
        custIntegralDetailExample.setAccountId(accountId);
        List<CustIntegralDetail> custIntegralDetailList = custIntegralService.getCustIntegralInfo(custIntegralDetailExample);
        String accountBlance = null;
        if (custIntegralDetailList != null && custIntegralDetailList.size() > 0) {
            accountBlance = custIntegralDetailList.get(0).getIntegralValidBlance();
        }
        return R.data(accountBlance);
    }

}
