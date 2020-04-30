package org.springclouddev.integral.vo;

public class AdjustIntegralVo {
	private String id;
	
	private String accountId;

    private String custId;

    private String adjustNum;

    private String status;
    
    private String statusName;

    private String integralValidDate;
    
    private String adjustType;
    
    private String transferAccountId;

    private String transferCustId;
    
    private String transferIntegralValidBlance;
    
    private String transferIntegralType;

    
    public String getTransferAccountId() {
		return transferAccountId;
	}

	public void setTransferAccountId(String transferAccountId) {
		this.transferAccountId = transferAccountId;
	}

	public String getTransferCustId() {
		return transferCustId;
	}

	public void setTransferCustId(String transferCustId) {
		this.transferCustId = transferCustId;
	}

	public String getTransferIntegralValidBlance() {
		return transferIntegralValidBlance;
	}

	public void setTransferIntegralValidBlance(String transferIntegralValidBlance) {
		this.transferIntegralValidBlance = transferIntegralValidBlance;
	}

	public String getTransferIntegralType() {
		return transferIntegralType;
	}

	public void setTransferIntegralType(String transferIntegralType) {
		this.transferIntegralType = transferIntegralType;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	private String adjustReason;
    
    private String integralType;
    
    public String getIntegralTypeId() {
		return integralTypeId;
	}

	public void setIntegralTypeId(String integralTypeId) {
		this.integralTypeId = integralTypeId;
	}

	private String integralTypeId;
    
    private String custName;
    
    private String integralValidBlance;

    private String crtUser;

    private String lstUpdUser;

    private String lstUpdTime;

    private String adjustDate;

	public String getCrtUser() {
		return crtUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getLstUpdUser() {
		return lstUpdUser;
	}

	public void setLstUpdUser(String lstUpdUser) {
		this.lstUpdUser = lstUpdUser;
	}

	public String getLstUpdTime() {
		return lstUpdTime;
	}

	public void setLstUpdTime(String lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}

	public String getAdjustDate() {
		return adjustDate;
	}

	public void setAdjustDate(String adjustDate) {
		this.adjustDate = adjustDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getIntegralValidBlance() {
		return integralValidBlance;
	}

	public void setIntegralValidBlance(String integralValidBlance) {
		this.integralValidBlance = integralValidBlance;
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

	public String getAdjustNum() {
		return adjustNum;
	}

	public void setAdjustNum(String adjustNum) {
		this.adjustNum = adjustNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIntegralValidDate() {
		return integralValidDate;
	}

	public void setIntegralValidDate(String integralValidDate) {
		this.integralValidDate = integralValidDate;
	}

	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public String getIntegralType() {
		return integralType;
	}

	public void setIntegralType(String integralType) {
		this.integralType = integralType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
    
    
}
