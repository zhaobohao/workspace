package org.springclouddev.integral.service.impl.data_dic;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.integral.entity.DataDicPrm;
import org.springclouddev.integral.mapper.DataDicPrmMapper;
import org.springclouddev.integral.service.data_dic.IDataDicPrmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DataDicPrmService extends BaseServiceImpl<DataDicPrmMapper, DataDicPrm> implements IDataDicPrmService {

	@Override
	public List<DataDicPrm> qryAllDataDicPrms() {
		return this.list();
	}

	@Override
	public DataDicPrm qryDataDicPrmById(Long id) {
		return this.getById(id);
	}

	@Override
	public int crtDataDicPrm(DataDicPrm sysDataDicRrm) {
		return baseMapper.insert(sysDataDicRrm);
	}

	@Override
	public int updDataDicPrm(DataDicPrm sysDataDicRrm) {
		return this.updateById(sysDataDicRrm)?1:0;
	}

	@Override
	public List<DataDicPrm> qryDataDicPrms(DataDicPrm DataDicPrm,int pageNum,int pageSize) {
		String code=DataDicPrm.getCode();
		String name=DataDicPrm.getName();
		Long pcode=DataDicPrm.getParentId();

		return baseMapper.selectPage(Condition.getPage(new Query().setCurrent(pageNum).setSize(pageSize)),Condition.getQueryWrapper(new DataDicPrm().setParentId(pcode)).like("code",code).like("name",name)).getRecords();
	}

	@Override
	@Transactional
	public boolean delDataDicPrmById(Long id) {
		this.removeById(id);
		return true;
	}

	@Override
	public List<DataDicPrm> qryDataDicPrmsByPcode(String code) {
		return this.list(Condition.getQueryWrapper(new DataDicPrm().setCode(code)));
	}	
	
	

}
