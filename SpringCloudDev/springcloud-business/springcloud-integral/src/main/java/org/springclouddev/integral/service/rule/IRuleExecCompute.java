package org.springclouddev.integral.service.rule;

import com.alibaba.fastjson.JSONObject;
import org.springclouddev.integral.aviator.common.ActResult;
import org.springclouddev.integral.aviator.common.RuleResult;
import org.springclouddev.integral.entity.RuleExp;

import java.util.List;

public interface IRuleExecCompute {

	/**
	 * 根据规则号，用户信息  ---  规则预计算
	 * @param ruleId	规则id
	 * @param userMsg	用户信息
	 * @return	计算结果
	 */
	RuleResult getRuleExecPrecomputedResultByRuleId(Long ruleId, JSONObject userMsg);

	/**
	 * 根据活动号，用户信息  ---  活动预计算
	 * @param ActId		活动号
	 * @param userMsg	用户信息
	 * @return	计算结果
	 */
	ActResult getRuleExecPrecomputedResultByActId(String actId, JSONObject userMsg);
	
	/**
	 * 全量规则计算
	 * @param userMsgList
	 * 返回信息
	 */
	List<RuleResult> getRuleExecPrecomputedResultRuleId(JSONObject userMsgList);
	
	
	List<RuleExp> getRuleExecAllList();
}
