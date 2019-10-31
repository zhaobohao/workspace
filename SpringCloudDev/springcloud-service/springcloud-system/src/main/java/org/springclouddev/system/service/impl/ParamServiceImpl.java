
package org.springclouddev.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.system.entity.Param;
import org.springclouddev.system.mapper.ParamMapper;
import org.springclouddev.system.service.IParamService;
import org.springclouddev.system.vo.ParamVO;
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
		return page.setRecords(SuperMapper.selectParamPage(page, param));
	}

}
