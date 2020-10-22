package org.springclouddev.system.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.system.entity.Role;
import org.springclouddev.system.vo.RoleVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 */
public interface RoleMapper extends SuperMapper<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	List<RoleVO> selectRolePage(IPage page, RoleVO role);

	/**
	 * 获取树形节点
	 *
	 * @param parentId
	 * @param excludeRole
	 * @return
	 */
	List<RoleVO> tree( String excludeRole,String parentId);

	/**
	 * 获取角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleNames(Long[] ids);

}
