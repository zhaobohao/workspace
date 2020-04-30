package org.springclouddev.integral.service.data_dic;


import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.GrpList;

import java.util.List;

public interface IGrpListService extends BaseService<GrpList> {
	List qryGrpLists(GrpList grpList, int pageNum, int pageSize);
	GrpList qryGrpListById(Long id);
	int crtGrpList(GrpList grpList);
	int updGrpList(GrpList grpList);
	boolean delGrpListById(Long id);
}
