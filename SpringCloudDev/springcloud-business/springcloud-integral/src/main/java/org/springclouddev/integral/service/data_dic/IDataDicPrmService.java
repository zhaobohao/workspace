package org.springclouddev.integral.service.data_dic;


import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.DataDicPrm;

import java.util.List;

;

public interface IDataDicPrmService extends BaseService<DataDicPrm> {
	List<DataDicPrm> qryAllDataDicPrms();
	List<DataDicPrm> qryDataDicPrms(DataDicPrm DataDicPrm, int pageNum, int pageSize);
	DataDicPrm qryDataDicPrmById(Long id);
	int crtDataDicPrm(DataDicPrm sysDataDicRrm);
	int updDataDicPrm(DataDicPrm sysDataDicRrm);
	boolean delDataDicPrmById(Long id);
	List<DataDicPrm> qryDataDicPrmsByPcode(String code);
}
