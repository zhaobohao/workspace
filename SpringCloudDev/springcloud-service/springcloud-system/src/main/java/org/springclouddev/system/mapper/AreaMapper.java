package org.springclouddev.system.mapper;

import org.springclouddev.system.entity.Area;
import org.springclouddev.system.vo.AreaVO;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.system.vo.MenuVO;

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
	/**
	 *  获取树形节点,获取指定parentId这一层的数据
	 * @param parentId 如果为空，返回所有树形结构数据
	 * @return
	 */
	List<AreaVO> tree(String parentId);
}
