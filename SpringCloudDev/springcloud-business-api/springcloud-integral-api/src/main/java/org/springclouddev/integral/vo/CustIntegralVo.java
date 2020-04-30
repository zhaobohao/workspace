package org.springclouddev.integral.vo;

import java.util.Date;

public class CustIntegralVo {
	private String accountId;

    private String custId;

    private String custName;

    private String phoneNum;

    private String custAddress;

    private String integralType;
    
    private String integralTypeId;

    private String accountStatus;
    
    public String getIntegralTypeId() {
		return integralTypeId;
	}

	public void setIntegralTypeId(String integralTypeId) {
		this.integralTypeId = integralTypeId;
	}

	private String accountStatusId;

    private String reserveColumn1;

    public String getAccountStatusId() {
		return accountStatusId;
	}

	public void setAccountStatusId(String accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

	private String reserveColumn2;

    private String reserveColumn3;
    
    private String blance;
    
    private String usedBlance;
    
    private String adjustType;
    
    private String adjustNum;
    
    private String adjustReason;
    
    private Date integralValidDate;
    public Date getIntegralValidDate() {
		return integralValidDate;
	}

	public void setIntegralValidDate(Date integralValidDate) {
		this.integralValidDate = integralValidDate;
	}


	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public String getIntegralType() {
		return integralType;
	}

	public void setIntegralType(String integralType) {
		this.integralType = integralType;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getReserveColumn1() {
		return reserveColumn1;
	}

	public void setReserveColumn1(String reserveColumn1) {
		this.reserveColumn1 = reserveColumn1;
	}

	public String getReserveColumn2() {
		return reserveColumn2;
	}

	public void setReserveColumn2(String reserveColumn2) {
		this.reserveColumn2 = reserveColumn2;
	}

	public String getReserveColumn3() {
		return reserveColumn3;
	}

	public void setReserveColumn3(String reserveColumn3) {
		this.reserveColumn3 = reserveColumn3;
	}

	public String getBlance() {
		return blance;
	}

	public void setBlance(String blance) {
		this.blance = blance;
	}

	public String getUsedBlance() {
		return usedBlance;
	}

	public void setUsedBlance(String usedBlance) {
		this.usedBlance = usedBlance;
	}

	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public String getAdjustNum() {
		return adjustNum;
	}

	public void setAdjustNum(String adjustNum) {
		this.adjustNum = adjustNum;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	private static final long serialVersionUID = 1L;
}
