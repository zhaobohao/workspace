
package org.springbootdev.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.core.tool.node.ForestNodeMerger;
import org.springbootdev.modules.system.entity.Area;
import org.springbootdev.modules.system.mapper.AreaMapper;
import org.springbootdev.modules.system.service.IAreaService;
import org.springbootdev.modules.system.vo.AreaVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行政区划 服务实现类
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<AreaMapper, Area> implements IAreaService {

	@Override
	public IPage<AreaVO> selectAreaPage(IPage<AreaVO> page, AreaVO area) {
		return page.setRecords(baseMapper.selectAreaPage(page, area));
	}

	@Override
	public List<AreaVO> tree(String parentId) {
		return ForestNodeMerger.merge(baseMapper.tree(parentId));
	}

}
