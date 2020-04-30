package org.springclouddev.integral.service.data_dic;


import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.GrpCls;

import java.util.List;

public interface IGrpClsService extends BaseService<GrpCls> {

	List qryGrpClses(GrpCls grpCls, int pageNum, int pageSize, String isInList);
	GrpCls qryGrpClsById(Long id);
	int crtGrpCls(GrpCls grpCls);
	int updGrpCls(GrpCls grpCls);
	boolean delGrpClsById(Long id);
}
