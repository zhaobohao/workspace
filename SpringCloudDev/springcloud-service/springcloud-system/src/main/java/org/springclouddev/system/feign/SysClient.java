
package org.springclouddev.system.feign;

import lombok.AllArgsConstructor;
import org.springclouddev.system.entity.Dept;
import org.springclouddev.system.entity.Role;
import org.springclouddev.system.service.IDeptService;
import org.springclouddev.system.service.IMenuService;
import org.springclouddev.system.service.IRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 系统服务Feign实现类
 *
 * @author zhaobohao
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class SysClient implements ISysClient {

	IDeptService deptService;

	IRoleService roleService;

	IMenuService menuService;

	@Override
	@GetMapping(API_PREFIX + "/getDeptName")
	public String getDeptName(Integer id) {
		return deptService.getById(id).getDeptName();
	}

	@Override
	@GetMapping(API_PREFIX + "/getDept")
	public Dept getDept(Integer id) {
		return deptService.getById(id);
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleName")
	public String getRoleName(Integer id) {
		return roleService.getById(id).getRoleName();
	}

	@Override
	@GetMapping(API_PREFIX + "/getRoleAlias")
	public String getRoleAlias(Integer id) {
		return roleService.getById(id).getRoleAlias();
	}

	@Override
	@GetMapping(API_PREFIX + "/getRole")
	public Role getRole(Integer id) {
		return roleService.getById(id);
	}

	@Override
	@GetMapping(API_PREFIX + "/getPermission")
	public List<String> getPermission(String roleIds) {
		return menuService.roleTreeKeys(roleIds);
	}
}
