
package org.springclouddev.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springclouddev.system.entity.Dept;
import org.springclouddev.system.entity.Dict;
import org.springclouddev.system.vo.DeptVO;

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
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId,String parentId);
	/**
	 * 新增或修改
	 * @param dept
	 * @return
	 */
	boolean submit(Dept dept);
}
