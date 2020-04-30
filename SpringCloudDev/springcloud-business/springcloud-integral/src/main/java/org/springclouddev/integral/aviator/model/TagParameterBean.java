package org.springclouddev.integral.aviator.model;

import org.springclouddev.integral.entity.ActPrm;
import org.springclouddev.integral.aviator.util.CreateExecUtil;

public class TagParameterBean {

	/**
	 * 标签名称
	 */
	private String tagCode;
	/**
	 * 参数
	 */
	private Object value;
	/**
	 * 参数数据
	 */
	private ActPrm actPrm;
	
	private String exp;
	
	public TagParameterBean(String tagCode, Object object, ActPrm actPrm) {
		super();
		this.tagCode = tagCode;
		this.value = object;
		this.actPrm = actPrm;
		this.exp = CreateExecUtil.getExec(this);
	}

	public String getTagName() {
		return tagCode;
	}

	public void setTagName(String tagName) {
		this.tagCode = tagName;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ActPrm getActPrm() {
		return actPrm;
	}

	public void setActPrm(ActPrm actPrm) {
		this.actPrm = actPrm;
	}

	@Override
	public String toString() {
		return " [tagCode=" + tagCode + ", value=" + value + ", actPrm=" + actPrm + ", exp=" + exp
				+ "]";
	}



	
}
