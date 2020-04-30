package org.springclouddev.integral.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ActIntegralInfoVo {
   
	    private String actCode;

	    private String actName;

	    private String marketActId;

	    private String department;

	    private String integralType;

	    private Integer integralLimitTimeType;

	    private String integralLimitYearNum;

	    private String integralEndMonth;

	    private Long prepareIntegralNum;

	    private Long prepareCount;

	    private String actBreif;

	    private String status;
	    
	    private String statusName;

	    private String ruleTeam;

	    private String opinion;

	    private String crtUser;
	    @JsonFormat(pattern="yyyy-MM-dd  HH:mm:ss",timezone= "GTM+8")
	    private Date crtTime;

	    private String lstUpdUser;
	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GTM+8")
	    private Date lstUpdTime;

	    private String reserveColumn1;

	    private String reserveColumn2;

	    private String reserveColumn3;

	    private static final long serialVersionUID = 1L;

		public String getActCode() {
			return actCode;
		}

		public void setActCode(String actCode) {
			this.actCode = actCode;
		}

		public String getActName() {
			return actName;
		}

		public void setActName(String actName) {
			this.actName = actName;
		}

		public String getMarketActId() {
			return marketActId;
		}

		public void setMarketActId(String marketActId) {
			this.marketActId = marketActId;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getIntegralType() {
			return integralType;
		}

		public void setIntegralType(String integralType) {
			this.integralType = integralType;
		}

		public Integer getIntegralLimitTimeType() {
			return integralLimitTimeType;
		}

		public void setIntegralLimitTimeType(Integer integralLimitTimeType) {
			this.integralLimitTimeType = integralLimitTimeType;
		}

		public String getIntegralLimitYearNum() {
			return integralLimitYearNum;
		}

		public void setIntegralLimitYearNum(String integralLimitYearNum) {
			this.integralLimitYearNum = integralLimitYearNum;
		}

		public String getIntegralEndMonth() {
			return integralEndMonth;
		}

		public void setIntegralEndMonth(String integralEndMonth) {
			this.integralEndMonth = integralEndMonth;
		}

		public Long getPrepareIntegralNum() {
			return prepareIntegralNum;
		}

		public void setPrepareIntegralNum(Long prepareIntegralNum) {
			this.prepareIntegralNum = prepareIntegralNum;
		}

		public Long getPrepareCount() {
			return prepareCount;
		}

		public void setPrepareCount(Long prepareCount) {
			this.prepareCount = prepareCount;
		}

		public String getActBreif() {
			return actBreif;
		}

		public void setActBreif(String actBreif) {
			this.actBreif = actBreif;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatusName() {
			return statusName;
		}

		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}

		public String getRuleTeam() {
			return ruleTeam;
		}

		public void setRuleTeam(String ruleTeam) {
			this.ruleTeam = ruleTeam;
		}

		public String getOpinion() {
			return opinion;
		}

		public void setOpinion(String opinion) {
			this.opinion = opinion;
		}

		public String getCrtUser() {
			return crtUser;
		}

		public void setCrtUser(String crtUser) {
			this.crtUser = crtUser;
		}

		public Date getCrtTime() {
			return crtTime;
		}

		public void setCrtTime(Date crtTime) {
			this.crtTime = crtTime;
		}

		public String getLstUpdUser() {
			return lstUpdUser;
		}

		public void setLstUpdUser(String lstUpdUser) {
			this.lstUpdUser = lstUpdUser;
		}

		public Date getLstUpdTime() {
			return lstUpdTime;
		}

		public void setLstUpdTime(Date lstUpdTime) {
			this.lstUpdTime = lstUpdTime;
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

		
	    
}
