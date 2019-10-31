
package org.springbootdev.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springbootdev.core.tool.node.ForestNodeMerger;
import org.springbootdev.modules.system.entity.Dept;
import org.springbootdev.modules.system.mapper.DeptMapper;
import org.springbootdev.modules.system.service.IDeptService;
import org.springbootdev.modules.system.vo.DeptVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author merryChen
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept) {
		return page.setRecords(SuperMapper.selectDeptPage(page, dept));
	}

	@Override
	public List<DeptVO> tree(String tenantId) {
		return ForestNodeMerger.merge(SuperMapper.tree(tenantId));
	}

}
