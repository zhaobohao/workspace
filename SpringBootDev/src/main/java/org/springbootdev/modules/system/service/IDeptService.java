
package org.springbootdev.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springbootdev.modules.system.entity.Dept;
import org.springbootdev.modules.system.vo.DeptVO;

import java.util.List;

/**
 * 服务类
 *
 * @author zhaobohao
 */
public interface IDeptService extends IService<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept);

	/**
	 * 树形结构
	 *
	 * @param parentId
	 * @return
	 */
	List<DeptVO> tree( String parentId);
	/**
	 * 新增或修改
	 * @param dept
	 * @return
	 */
	boolean submit(Dept dept);
}
