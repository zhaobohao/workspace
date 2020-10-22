package org.springclouddev.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.secure.utils.SecureUtil;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.node.ForestNodeMerger;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.system.entity.Dept;
import org.springclouddev.system.mapper.DeptMapper;
import org.springclouddev.system.service.IDeptService;
import org.springclouddev.system.vo.DeptVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
	public List<DeptVO> tree(String parentId) {
		return ForestNodeMerger.merge(baseMapper.tree(parentId));
	}

	@Override
	public boolean submit(Dept dept) {
		if(null==dept.getId())
		{
			dept.setIsLeaf(0);
			//维护父组件的isleaf字段
			Dept parent=new Dept();
			parent.setId(dept.getParentId());
			parent.setIsLeaf(1);
			updateById(parent);
		}
		return saveOrUpdate(dept);
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		List<Dept> deptList = baseMapper.selectList(Wrappers.<Dept>query().lambda().eq(Dept::getTenantId, tenantId).in(Dept::getDeptName, Func.toStrList(deptNames)));
		if (deptList != null && deptList.size() > 0) {
			return deptList.stream().map(dept -> Func.toStr(dept.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return baseMapper.getDeptNames(Func.toLongArray(deptIds));
	}

}
