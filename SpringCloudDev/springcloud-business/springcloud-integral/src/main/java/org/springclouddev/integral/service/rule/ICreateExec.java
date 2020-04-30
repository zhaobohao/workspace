package org.springclouddev.integral.service.rule;

public interface ICreateExec {

	/**
	 * 生成最终表达式
	 * @param list
	 * @return
	 */
//	void insertRuleMsg(RuleExp ruleExp, List<TagParameterBean> list,TagIntegralResult tagIntegralResult);
	
	void insertRuleMsg(Long ruleId);
	
//	void updateRuleMsg(RuleExp ruleExp, String rule,String integralRes);
//	void updateRuleMsg(RuleExp ruleExp, List<TagParameterBean> list,TagIntegralResult tagIntegralResult);
	
//	String getJointExec(List<TagParameterBean> list,TagIntegralResult tagIntegralResult);
	
	void insertActRuleRelation();
	
}
