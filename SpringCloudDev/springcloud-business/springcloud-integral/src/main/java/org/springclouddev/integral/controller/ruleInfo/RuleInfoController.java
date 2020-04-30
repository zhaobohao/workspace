package org.springclouddev.integral.controller.ruleInfo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.common.tool.CommonUtil;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.entity.RuleInfo;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.ruleInfo.IRuleInfoService;
import org.springclouddev.integral.vo.RuleInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api("规则相关API")
@RestController
@RequestMapping("/ruleInfo")
public class RuleInfoController {
	private static Logger logger = LoggerFactory.getLogger(RuleInfoController.class);
	@Autowired
	IRuleInfoService ruleInfoService;
	
	
	@ApiOperation("规则列表分页查询")
	@GetMapping("/pageList")
	public Object queryRuleInfoPageList(
			@RequestParam(required=false) String ruleName,
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="10") int limit){
		try {
			List<RuleInfo> list = ruleInfoService.getRuleInfoList(ruleName, page, limit);
			List<RuleInfoVo> collect = list.stream().map(value->getRuleInfoVo(value)).collect(Collectors.toList());
			IPage<RuleInfoVo> page1 = new Page<RuleInfoVo>();
			page1.setTotal(collect.size()).setSize(limit).setCurrent(page).setRecords(collect);
			return R.data(page1);
		} catch (Exception e) {
			logger.error("查询规则列表异常："+e);
			return R.fail(ReturnCode.SYSTEM_EXCEPTION);
		}
	}
	

	@ApiOperation("规则审核列表分页查询")
	@GetMapping("/auditList")
	public Object queryRuleInfoAuditPageList(
			@RequestParam(required=false) String ruleName,
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="10") int limit){
		try {
			List<RuleInfo> list = ruleInfoService.getRuleInfoAuditList(ruleName, page, limit);
			List<RuleInfoVo> collect = list.stream().map(value->getRuleInfoVo(value)).collect(Collectors.toList());
			IPage<RuleInfoVo> page1 = new Page<RuleInfoVo>();
			page1.setTotal(collect.size()).setSize(limit).setCurrent(page).setRecords(collect);
			return R.data(page1);
		} catch (Exception e) {
			logger.error("查询规则列表异常："+e);
			return R.fail(ReturnCode.SYSTEM_EXCEPTION);
		}
	}
	
	private RuleInfoVo getRuleInfoVo(RuleInfo rule){
		RuleInfoVo vo = new RuleInfoVo();
		BeanUtil.copyProperties(rule, vo);
		vo.setStatus(rule.getRuleStatus());
		vo.setStatusName(LocalDictCache.getDicPrmById(rule.getRuleStatus()).getName());
		
		return vo;
	}
	
	@ApiOperation("活动下拉规则列表查询")
	@GetMapping("/list")
	public Object queryRuleInfoList(
			@RequestParam(required=false) String ruleName) {
		try {
			List<RuleInfo> list = ruleInfoService.getRuleInfoList(ruleName);
			List<RuleInfoVo> collect = list.stream().map(value->getRuleInfoVo(value)).collect(Collectors.toList());
			return R.data(collect);
		} catch (Exception e) {
			logger.error("查询规则列表异常："+e);
			return R.fail(ReturnCode.SYSTEM_EXCEPTION);
		}
	}
	
	@ApiOperation("查询规则信息")
	@GetMapping("/qryRuleInfo")
	public Object selectRuleInfo(@RequestParam Long ruleId) throws ParseException{
		try {
			RuleInfo selectRuleInfo = ruleInfoService.selectRuleInfo(ruleId);
			if(selectRuleInfo != null) {				
				return R.data(selectRuleInfo);
			}else {
				return R.fail(ReturnCode.NO_THIS_ITEM_IN_DATABASE);
			}
		} catch (Exception e) {
			logger.info("新增规则失败："+e+e.getMessage());
			return R.fail(ReturnCode.ADD_EXCEPTION);
		}
	}
	
	@ApiOperation("校验规则名称是否重复")
	@PostMapping("/verify")
	public Object verifyRuleName(@RequestBody RuleInfo ruleInfo) {
		boolean verufyRuleName = ruleInfoService.verufyRuleName(ruleInfo);
		Map<String, Boolean> data = Maps.newHashMap();
		if(!verufyRuleName) {
			data.put("isDuplicate", true);
			return R.data(data);
		} else {
			data.put("isDuplicate", false);
			return R.data(data);
		}
	}
	
	@ApiOperation("规则信息新增")
	@PostMapping("/add")
	public Object addRuleInfo(@RequestBody RuleInfo ruleInfo) throws ParseException{
		try {
			ruleInfoService.insertRuleInfo(ruleInfo);
			return R.success("操作成功");
		} catch (Exception e) {
			logger.info("新增规则失败："+e+e.getMessage());
			return R.fail(ReturnCode.ADD_EXCEPTION);
		}
	}
	
	@ApiOperation("修改规则信息")
	@PostMapping("/upd")
	public Object updRuleInfo(@RequestBody RuleInfo ruleInfo) throws ParseException{
		try {
			if(ruleInfo.getId() == 0) {
				return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
			}
//			if(StrUtil.isNotBlank(ruleInfo.getRuleName())) {
//				boolean verufyRuleName = ruleInfoService.verufyRuleName(ruleInfo.getRuleName(),ruleInfo.getRuleId());
//				if(!verufyRuleName) {
//					return R.fail(ReturnCode.RULE_NAME_REPEAT);
//				}
//			}
			boolean updateRuleInfo = ruleInfoService.updateRuleInfo(ruleInfo);
			if(updateRuleInfo) {
				return R.success("操作成功");
			}else {
				return R.fail(ReturnCode.HAS_AUDIT_EXCEPTION);
			}
		} catch (Exception e) {
			logger.info("更新失败："+e+e.getMessage());
			return R.fail(ReturnCode.UPD_EXCEPTION);
		}
	}
	
	
	
	@ApiOperation("删除规则信息")
	@PostMapping("/delete")
	public Object deleteRuleInfoById(@RequestBody RuleInfo ruleInfo){
		try {
			if(ruleInfo.getId() == 0) {
				return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
			}
			boolean delRuleInfo = ruleInfoService.delRuleInfo(ruleInfo.getId());
			if(delRuleInfo) {
				return R.success("操作成功");
			}else {
				return R.fail(ReturnCode.HAS_AUDIT_EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("删除规则信息失败："+e+e.getMessage());
			return R.fail(ReturnCode.DEL_EXCEPTION);
		}
	}
	
	@ApiOperation("规则信息送审")
	@PostMapping("/pushAudit")
	public Object auditRuleInfoById(@RequestBody RuleInfo ruleInfo) throws ParseException{
		try {
			if(ruleInfo.getId() == 0) {
				return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
			}
			boolean flag = ruleInfoService.pushRuleInfoAudit(ruleInfo);
			if(flag) {
				return R.success("操作成功");
			}else {
				return R.fail(ReturnCode.AUDIT_EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("送审失败："+e);
			return R.fail(ReturnCode.AUDIT_EXCEPTION);
		}
	}
	
	@ApiOperation("规则信息审核通过、拒绝")
	@PostMapping("/passedAudit")
	public Object passedAuditRuleInfoById(@RequestBody RuleInfoVo ruleInfoVo) throws ParseException{
		try {
			if(ruleInfoVo.getRuleId() == 0) {
				return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
			}
			RuleInfo ruleInfo = new RuleInfo();
			ruleInfo.setId(ruleInfoVo.getRuleId());
			ruleInfo.setRuleStatus(ruleInfoVo.getStatus( ));
			boolean flag = ruleInfoService.passedRuleInfoAudit(ruleInfo);
			if(flag) {
				return R.success("操作成功");
			}else {
				return R.fail(ReturnCode.AUDIT_EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("送审失败："+e);
			return R.fail(ReturnCode.AUDIT_EXCEPTION);
		}
	}
}
