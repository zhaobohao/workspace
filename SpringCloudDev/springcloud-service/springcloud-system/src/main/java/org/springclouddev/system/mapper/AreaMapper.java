
package org.springclouddev.system.mapper;

import org.springclouddev.system.entity.Area;
import org.springclouddev.system.vo.AreaVO;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 行政区划 Mapper 接口
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
public interface AreaMapper extends SuperMapper<Area> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param area
	 * @return
	 */
	List<AreaVO> selectAreaPage(IPage page, AreaVO area);

}
