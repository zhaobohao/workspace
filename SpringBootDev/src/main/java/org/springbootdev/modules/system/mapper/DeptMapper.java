
package org.springbootdev.modules.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.SuperMapper;
import org.springbootdev.modules.system.entity.Dept;
import org.springbootdev.modules.system.vo.DeptVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 */
public interface DeptMapper extends SuperMapper<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	List<DeptVO> selectDeptPage(IPage page, DeptVO dept);

	/**
	 *  获取树形节点,获取指定parentId这一层的数据
	 * @param tenantId
	 * @param parentId 如果为空，返回所有树形结构数据
	 * @return
	 */
	List<DeptVO> tree(String tenantId, String parentId);
}
