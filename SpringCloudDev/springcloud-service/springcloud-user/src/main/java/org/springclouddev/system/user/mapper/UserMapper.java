
package org.springclouddev.system.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.system.user.entity.User;
import org.springclouddev.system.user.excel.UserExcel;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 */

@Mapper
public interface UserMapper extends SuperMapper<User> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	List<User> selectUserPage(IPage page, User user);

	/**
	 * 获取用户
	 *
	 * @param tenantId
	 * @param account
	 * @param password
	 * @return
	 */
	User getUser(String tenantId, String account, String password);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleName(String[] roleIds);

	/**
	 * 获取角色别名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleAlias(String[] roleIds);

	/**
	 * 获取部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptName(String[] ids);

	/**
	 * 获取菜单别名，当做资源名称使用
	 * @param roleIds
	 * @return
	 */
	List<String> getResources(String[] roleIds);

	/**
	 * 获取导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(@Param("ew") Wrapper<User> queryWrapper);
}
