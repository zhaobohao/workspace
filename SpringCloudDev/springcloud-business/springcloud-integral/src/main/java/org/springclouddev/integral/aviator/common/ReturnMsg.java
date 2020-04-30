package org.springclouddev.integral.aviator.common;

public enum ReturnMsg {

	SUCCESS("000000","成功"),
	EXEC_ERROR("000001","计算失败，发生异常"),
	RULE_LIST_NULL("000002","规则列表信息为空"),
	JSON_ERROR("000003","用户信息数据异常"),
	NULL("000004","规则信息和活动信息为空"),
	
	; 
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private ReturnMsg(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
