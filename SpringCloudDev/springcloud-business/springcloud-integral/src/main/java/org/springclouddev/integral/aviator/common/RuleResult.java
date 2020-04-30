package org.springclouddev.integral.aviator.common;

public class RuleResult {

	private String resultCode;
	private String resultMsg;
		
	private long result;
	
	private Long ruleId;
	private String ruleName;
	private String type;

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getResultCode() {
		return resultCode;
	} 

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public void setResult(long result) {
		this.result = result;
	}

	public long getResult() {
		return result;
	}

	public RuleResult(String resultCode, String resultMsg, long execute,Long ruleId,String ruleName) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.result = execute;
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.type = "rule";
	}


	public RuleResult(String resultCode, String resultMsg, long execute) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.result = execute;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
