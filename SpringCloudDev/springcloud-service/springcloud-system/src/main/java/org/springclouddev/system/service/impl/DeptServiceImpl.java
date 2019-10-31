
package org.springclouddev.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springclouddev.core.tool.node.ForestNodeMerger;
import org.springclouddev.system.entity.Dept;
import org.springclouddev.system.mapper.DeptMapper;
import org.springclouddev.system.service.IDeptService;
import org.springclouddev.system.vo.DeptVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author zhaobohao
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept) {
		return page.setRecords(baseMapper.selectDeptPage(page, dept));
	}

	@Override
	public List<DeptVO> tree(String tenantId) {
		return ForestNodeMerger.merge(baseMapper.tree(tenantId));
	}

}
