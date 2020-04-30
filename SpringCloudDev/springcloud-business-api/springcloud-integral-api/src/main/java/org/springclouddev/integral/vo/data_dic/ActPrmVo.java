package org.springclouddev.integral.vo.data_dic;


import org.springclouddev.integral.entity.ActPrm;
public class ActPrmVo extends ActPrm {
	private String sysName;//所属系统名称
	private String pCoderName;//所属父编码名称03
	private String dataModeName;//数据方式名称02
	private String hideDeptName;//隐藏部门名称01
	private String selectorModeName;//选择方式名称04
	private String conditionTypeName;//条件类型名称05；
	private String startSignName;//启用标识名称
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getpCoderName() {
		return pCoderName;
	}
	public void setpCoderName(String pCoderName) {
		this.pCoderName = pCoderName;
	}
	public String getDataModeName() {
		return dataModeName;
	}
	public void setDataModeName(String dataModeName) {
		this.dataModeName = dataModeName;
	}
	public String getHideDeptName() {
		return hideDeptName;
	}
	public void setHideDeptName(String hideDeptName) {
		this.hideDeptName = hideDeptName;
	}
	public String getSelectorModeName() {
		return selectorModeName;
	}
	public void setSelectorModeName(String selectorModeName) {
		this.selectorModeName = selectorModeName;
	}
	public String getConditionTypeName() {
		return conditionTypeName;
	}
	public void setConditionTypeName(String conditionTypeName) {
		this.conditionTypeName = conditionTypeName;
	}
	public String getStartSignName() {
		return startSignName;
	}
	public void setStartSignName(String startSignName) {
		this.startSignName = startSignName;
	}
	
	
	
}
