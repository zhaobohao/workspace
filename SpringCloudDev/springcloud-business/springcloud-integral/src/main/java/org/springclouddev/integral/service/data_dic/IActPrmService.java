package org.springclouddev.integral.service.data_dic;


import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.ActPrm;

import java.util.List;

public interface IActPrmService extends BaseService<ActPrm> {

	List qryActPrms(ActPrm sysActPrm, int pageNum, int pageSize);
	ActPrm qryActPrmById(String code);
	int crtActPrm(ActPrm sysActPrm);
	int updActPrm(ActPrm sysActPrm);
	boolean delActPrm(Long id);
	
	List qryAllStartActPrms();
}
