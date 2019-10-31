
package org.springbootdev.modules.system.mapper;

import org.springbootdev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.modules.system.entity.Dept;
import org.springbootdev.modules.system.vo.DeptVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author merryChen
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
	 * 获取树形节点
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

}
