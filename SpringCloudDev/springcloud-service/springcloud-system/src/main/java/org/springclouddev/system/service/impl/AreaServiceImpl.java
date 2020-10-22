package org.springclouddev.system.service.impl;

import org.springclouddev.core.tool.node.ForestNodeMerger;
import org.springclouddev.system.entity.Area;
import org.springclouddev.system.vo.AreaVO;
import org.springclouddev.system.mapper.AreaMapper;
import org.springclouddev.system.service.IAreaService;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.system.vo.MenuVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

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
