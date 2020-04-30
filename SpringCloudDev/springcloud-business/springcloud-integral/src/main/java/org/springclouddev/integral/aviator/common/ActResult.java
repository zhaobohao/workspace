package org.springclouddev.integral.aviator.common;

import java.util.List;

public class ActResult {

	private String resultCode;
	private String resultMsg;
		
	private long result;
	private String type;

	private String actCode;
	private String actName;
	private List<RuleResult> list;

	public String getActId() {
		return actCode;
	}

	public void setActId(String actId) {
		this.actCode = actId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
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


	public ActResult(String resultCode, String resultMsg, long execute,String actId,String actName,List<RuleResult> list) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.result = execute;
		this.actCode = actId;
		this.actName = actName;
		this.type = "act";
		this.list = list;
	}

	public ActResult(String resultCode, String resultMsg, long execute) {
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

	public List<RuleResult> getList() {
		return list;
	}

	public void setList(List<RuleResult> list) {
		this.list = list;
	}

	
}
