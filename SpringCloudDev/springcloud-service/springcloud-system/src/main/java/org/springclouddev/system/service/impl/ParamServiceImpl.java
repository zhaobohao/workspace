package org.springclouddev.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.system.entity.Param;
import org.springclouddev.system.mapper.ParamMapper;
import org.springclouddev.system.service.IParamService;
import org.springclouddev.system.vo.ParamVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zhaobohao
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param) {
		return page.setRecords(baseMapper.selectParamPage(page, param));
	}

	@Override
	@Cacheable(value="globalParam",key="#key")
	public String getParamValueByKey(String key) {
		return baseMapper.selectOne(Condition.getQueryWrapper(new Param().setParamKey(key))).getParamValue();
	}

}
