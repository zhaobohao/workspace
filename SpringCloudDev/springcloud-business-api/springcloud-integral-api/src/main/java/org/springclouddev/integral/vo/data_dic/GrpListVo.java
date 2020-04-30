package org.springclouddev.integral.vo.data_dic;


import org.springclouddev.integral.entity.GrpList;

public class GrpListVo extends GrpList {

	private String sortName;
	private String typeName;
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
