package org.springclouddev.system.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.system.entity.Param;
import org.springclouddev.system.vo.ParamVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 */
public interface ParamMapper extends SuperMapper<Param> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	List<ParamVO> selectParamPage(IPage page, ParamVO param);

}
