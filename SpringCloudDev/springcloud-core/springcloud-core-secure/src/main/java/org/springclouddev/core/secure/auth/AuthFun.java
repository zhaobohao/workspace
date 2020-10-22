package org.springclouddev.core.secure.auth;

import org.springclouddev.core.secure.utils.SecureUtil;
import org.springclouddev.core.tool.constant.RoleConstant;
import org.springclouddev.core.tool.utils.CollectionUtil;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.core.tool.utils.StringUtil;

/**
 * 权限判断
 *
 * @author zhaobohao
 */
public class AuthFun {

	/**
	 * 放行所有请求
	 *
	 * @return {boolean}
	 */
	public boolean permitAll() {
		return true;
	}

	/**
	 * 只有超管角色才可访问
	 *
	 * @return {boolean}
	 */
	public boolean denyAll() {
		return hasRole(RoleConstant.ADMIN);
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param role 单角色
	 * @return {boolean}
	 */
	public boolean hasRole(String role) {
		return hasAnyRole(role);
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param role 角色集合
	 * @return {boolean}
	 */
	public boolean hasAnyRole(String... role) {
		String userRole = SecureUtil.getUser().getRoleName();
		if (StringUtil.isBlank(userRole)) {
			return false;
		}
		String[] roles = Func.toStrArray(userRole);
		for (String r : role) {
			if (CollectionUtil.contains(roles, r)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param resource 单资源
	 * @return {boolean}
	 */
	public boolean hasResource(String resource) {
		return hasAnyResource(resource);
	}


	/**
	 * 判断是否有该角色权限
	 *
	 * @param resource 资源集合
	 * @return {boolean}
	 */
	public boolean hasAnyResource(String... resource) {
		String userResources = SecureUtil.getUser().getResources();
		if (StringUtil.isBlank(userResources)) {
			return false;
		}
		String[] resources = Func.toStrArray(userResources);
		for (String r : resource) {
			if (CollectionUtil.contains(resources, r)) {
				return true;
			}
		}
		return false;
	}

}
