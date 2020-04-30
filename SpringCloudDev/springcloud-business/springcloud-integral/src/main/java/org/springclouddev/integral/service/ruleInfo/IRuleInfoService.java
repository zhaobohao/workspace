package org.springclouddev.integral.service.ruleInfo;


import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.RuleInfo;

import java.util.List;

public interface IRuleInfoService extends BaseService<RuleInfo> {
	
//	List<ActPrm> getInitRuleConfig(String sysId,String depId);
	
	
	List<RuleInfo> getRuleInfoList(String ruleName, int page, int limit);

	List<RuleInfo> getRuleInfoAuditList(String ruleName, int page, int limit);
	List<RuleInfo> getRuleInfoList(String ruleName);
	
	boolean delRuleInfo(Long ruleId);

	void insertRuleInfo(RuleInfo rule);
	
	boolean updateRuleInfo(RuleInfo rule);
	
	RuleInfo selectRuleInfo(Long ruleId);
	boolean passedRuleInfoAudit(RuleInfo rule);

	boolean unPassedRuleInfoAudit(RuleInfo rule);
	
	boolean pushRuleInfoAudit(RuleInfo rule);
	
	boolean verufyRuleName(RuleInfo rule);
	
	long getStatusCount(String status);
}
