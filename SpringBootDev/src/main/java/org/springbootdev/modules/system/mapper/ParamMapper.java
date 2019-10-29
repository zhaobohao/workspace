
package org.springbootdev.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.modules.system.entity.Param;
import org.springbootdev.modules.system.vo.ParamVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author merryChen
 */
public interface ParamMapper extends BaseMapper<Param> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	List<ParamVO> selectParamPage(IPage page, ParamVO param);

}
