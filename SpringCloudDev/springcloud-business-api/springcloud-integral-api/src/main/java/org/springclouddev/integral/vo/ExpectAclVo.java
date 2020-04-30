package org.springclouddev.integral.vo;

import com.alibaba.fastjson.JSONObject;

public class ExpectAclVo {

	private Long ruleId;
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public JSONObject getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(JSONObject userMsg) {
		this.userMsg = userMsg;
	}
	private String actId;
	private JSONObject userMsg;
	
	
}
