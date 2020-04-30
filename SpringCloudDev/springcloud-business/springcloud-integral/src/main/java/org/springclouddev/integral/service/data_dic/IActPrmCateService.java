package org.springclouddev.integral.service.data_dic;


import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.ActPrm;
import org.springclouddev.integral.entity.ActPrmCate;

import java.util.List;

public interface IActPrmCateService extends BaseService<ActPrmCate> {

	List qryCatesByApCode(String apCode, int pageNum, int pageSize);
	ActPrmCate qryCateByApCodeAndCode(String apCode, String code);
	int crtCate(ActPrmCate actPrmCate);
	int updCateByApCodeAndCode(ActPrmCate actPrmCate);
	boolean delCateByApCodeAndCode(String apCode, String code);
	List qryStartCatesByApCode(String apCode);
}
