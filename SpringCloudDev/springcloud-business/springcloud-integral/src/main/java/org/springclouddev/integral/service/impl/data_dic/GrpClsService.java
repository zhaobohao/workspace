package org.springclouddev.integral.service.impl.data_dic;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.integral.entity.GrpCls;
import org.springclouddev.integral.mapper.GrpClsMapper;
import org.springclouddev.integral.service.data_dic.IGrpClsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrpClsService extends BaseServiceImpl<GrpClsMapper, GrpCls> implements IGrpClsService {
	
	@Override
	public List<GrpCls> qryGrpClses(GrpCls grpCls, int pageNum, int pageSize,String isInList) {
		String listId=grpCls.getListId();
		String code= grpCls.getCode();
		String name=grpCls.getName();
		GrpCls example=new GrpCls();
		example.setType(grpCls.getType());
		QueryWrapper<GrpCls> qw= Condition.getQueryWrapper(example);
		if(!StringUtils.isEmpty(listId)&& StringUtils.equals("01", isInList)) {
		qw.like("listId",listId);
		}else if (!StringUtils.isEmpty(listId)&& StringUtils.equals("02", isInList)) {
			qw.notLike("listId",listId);
		}
		if(!StringUtils.isEmpty("code")) {
			qw.like("code",code);
		}
		if(!StringUtils.isEmpty("name")) {
			qw.like("name",name);
		}
		return baseMapper.selectPage(Condition.getPage(new Query().setSize(pageSize).setCurrent(pageNum)),qw).getRecords();
	}

	@Override
	public GrpCls qryGrpClsById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public int crtGrpCls(GrpCls grpCls) {
		return baseMapper.insert(grpCls);
	}

	@Override
	public int updGrpCls(GrpCls grpCls) {
		return baseMapper.updateById(grpCls);
	}

	@Override
	public boolean delGrpClsById(Long id) {
		baseMapper.deleteById(id);
		return true;
	}

}
