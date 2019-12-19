
package org.springbootdev.modules.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.SuperMapper;
import org.springbootdev.modules.system.entity.Param;
import org.springbootdev.modules.system.vo.ParamVO;

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
