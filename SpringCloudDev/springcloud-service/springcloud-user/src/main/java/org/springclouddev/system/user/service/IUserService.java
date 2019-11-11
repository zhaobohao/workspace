
package org.springclouddev.system.user.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.system.user.entity.User;
import org.springclouddev.system.user.entity.UserInfo;

import java.util.List;

/**
 * 服务类
 *
 * @author zhaobohao
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增或修改用户
	 *
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @param password
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account, String password);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);
	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(Integer userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleName(String roleIds);
	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleAlians(String roleIds);
	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptName(String deptIds);
}
