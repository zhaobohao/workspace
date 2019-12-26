
package org.springclouddev.system.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.system.entity.Dept;
import org.springclouddev.system.vo.DeptVO;

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
	 * @param parentId 如果为空，返回所有树形结构数据
	 * @return
	 */
	List<DeptVO> tree(String parentId);
}
