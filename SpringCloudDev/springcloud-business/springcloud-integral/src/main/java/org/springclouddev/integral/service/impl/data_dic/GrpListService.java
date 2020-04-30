package org.springclouddev.integral.service.impl.data_dic;

import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.integral.entity.GrpList;
import org.springclouddev.integral.mapper.GrpListMapper;
import org.springclouddev.integral.service.data_dic.IGrpListService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GrpListService  extends BaseServiceImpl<GrpListMapper, GrpList> implements IGrpListService {


	
	@Override
	public List<GrpList> qryGrpLists(GrpList grpList, int pageNum, int pageSize) {
		String code=grpList.getCode();
		String name=grpList.getName();
		String type=grpList.getType();
		String sort=grpList.getSort();
		Map<String,Object> map=new HashMap<>();
			map.put("code_like",code);
			map.put("name_like",name);
			map.put("type",type);
			map.put("sort",sort);

		return baseMapper.selectPage(Condition.getPage(new Query().setCurrent(pageNum).setSize(pageSize)),Condition.getQueryWrapper(map,GrpList.class)).getRecords();
	}

	@Override
	public GrpList qryGrpListById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public int crtGrpList(GrpList grpList) {
		return baseMapper.insert(grpList);
	}

	@Override
	public int updGrpList(GrpList grpList) {
		return baseMapper.updateById(grpList);
	}

	@Override
	public boolean delGrpListById(Long id) {
		baseMapper.deleteById(id);
		return true;
	}

}
