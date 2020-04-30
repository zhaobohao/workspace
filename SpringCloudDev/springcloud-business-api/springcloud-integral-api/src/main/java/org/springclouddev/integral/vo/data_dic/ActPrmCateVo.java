package org.springclouddev.integral.vo.data_dic;


import org.springclouddev.integral.entity.ActPrmCate;

public class ActPrmCateVo extends ActPrmCate {
	private String startSignName;
	private String pName;
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getStartSignName() {
		return startSignName;
	}
	public void setStartSignName(String startSignName) {
		this.startSignName = startSignName;
	}

}
