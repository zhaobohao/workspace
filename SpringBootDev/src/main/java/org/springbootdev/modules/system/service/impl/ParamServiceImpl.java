
package org.springbootdev.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.modules.system.entity.Param;
import org.springbootdev.modules.system.mapper.ParamMapper;
import org.springbootdev.modules.system.service.IParamService;
import org.springbootdev.modules.system.vo.ParamVO;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author merryChen
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param) {
		return page.setRecords(baseMapper.selectParamPage(page, param));
	}

}
