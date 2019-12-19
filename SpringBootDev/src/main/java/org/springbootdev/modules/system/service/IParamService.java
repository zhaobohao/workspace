
package org.springbootdev.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.BaseService;
import org.springbootdev.modules.system.entity.Param;
import org.springbootdev.modules.system.vo.ParamVO;

/**
 * 服务类
 *
 * @author zhaobohao
 */
public interface IParamService extends BaseService<Param> {

	/***
	 * 自定义分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param);

	String getParamValueByKey(String key);
}

