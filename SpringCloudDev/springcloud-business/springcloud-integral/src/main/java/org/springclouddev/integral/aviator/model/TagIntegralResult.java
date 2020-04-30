package org.springclouddev.integral.aviator.model;

import com.alibaba.fastjson.JSONArray;
import org.springclouddev.integral.aviator.common.CommonCode;
import org.springclouddev.integral.aviator.util.CreateExecUtil;

public class TagIntegralResult {

	private Integer id;
	
	/**
	 * 金额/笔数名称
	 */
	private String tagCode;
	
	/**
	 * 金额超出
	 */
	private Integer beyondAmount;
	
	/**
	 * 积分比率
	 * value1	积分
	 * value2	金额
	 */
	private Integer value1;
	private Integer value2;
	/**
	 * 封顶分数
	 */
	private Integer topGrade;

	/**
	 * 每笔固定积分
	 */
	private Integer fixedIntegral;
	
	/**
	 * 交易笔数
	 */
	private Integer transactionsNumber;
	
	/**
	 * 积分结果表达式
	 */
	private String TagIntegralResultExp;

	public String getTagIntegralResultExp() {
		return TagIntegralResultExp;
	}

	public void setTagIntegralResultExp(String tagIntegralResultExp) {
		TagIntegralResultExp = tagIntegralResultExp;
	}

	public Integer getBeyondAmount() {
		return beyondAmount;
	}

	public void setBeyondAmount(Integer beyondAmount) {
		this.beyondAmount = beyondAmount;
	}

	public Integer getTopGrade() {
		return topGrade;
	}

	public void setTopGrade(Integer topGrade) {
		this.topGrade = topGrade;
	}

	public Integer getFixedIntegral() {
		return fixedIntegral;
	}

	public void setFixedIntegral(Integer fixedIntegral) {
		this.fixedIntegral = fixedIntegral;
	}

	public Integer getTransactionsNumber() {
		return transactionsNumber;
	}

	public void setTransactionsNumber(Integer transactionsNumber) {
		this.transactionsNumber = transactionsNumber;
	}
	
	

	public TagIntegralResult(Integer id, Integer beyondAmount, Object array, Integer topGrade,
			Integer fixedIntegral, Integer transactionsNumber) {
		if(id == 0) {
			this.setTagIntegralResult(CommonCode.TXN_AMT, beyondAmount, Integer.parseInt(((JSONArray)array).getString(1)),Integer.parseInt(((JSONArray)array).getString(0)), topGrade);
		}else {
			this.setTagIntegralResult(CommonCode.TXN_CNT, transactionsNumber, fixedIntegral);
		}
	}

	/**
	 * 
	 * @param tagCode
	 * @param beyondAmount	需要超出金额
	 * @param integralRatio	金额积分装换比例
	 * @param topGrade		最大积分数
	 */
	private void setTagIntegralResult(String tagCode, Integer beyondAmount, Integer value1,Integer value2, Integer topGrade) {
		this.tagCode = tagCode;
		this.beyondAmount = beyondAmount;
		this.setValue1(value1);
		this.setValue2(value2);
		this.topGrade = topGrade;
		this.TagIntegralResultExp = CreateExecUtil.getTagIntegralResultExpByBeyondAmount(this);
	}

	/**
	 * 
	 * @param tagCode
	 * @param transactionsNumber	笔数
	 * @param fixedIntegral			每笔送积分数
	 */
	private void setTagIntegralResult(String tagCode,
			Integer transactionsNumber, 
			Integer fixedIntegral) {
		this.tagCode = tagCode;
		this.fixedIntegral = fixedIntegral;
		this.transactionsNumber = transactionsNumber;
		this.TagIntegralResultExp = CreateExecUtil.getTagIntegralResultExpByTransNumber(this);
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue1() {
		return value1;
	}

	public void setValue1(Integer value1) {
		this.value1 = value1;
	}

	public Integer getValue2() {
		return value2;
	}

	public void setValue2(Integer value2) {
		this.value2 = value2;
	}
	
}
