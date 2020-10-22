package org.springclouddev.system.service;

import org.springclouddev.system.entity.Area;
import org.springclouddev.system.vo.AreaVO;
import org.springclouddev.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.system.vo.MenuVO;

import java.util.List;

/**
 * 行政区划 服务类
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
public interface IAreaService extends BaseService<Area> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param area
	 * @return
	 */
	IPage<AreaVO> selectAreaPage(IPage<AreaVO> page, AreaVO area);
	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<AreaVO> tree(String parentId);
}
