
package org.springclouddev.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.node.INode;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.system.entity.Dept;
import org.springclouddev.system.entity.Role;
import org.springclouddev.system.service.IRoleService;
import org.springclouddev.system.vo.RoleVO;
import org.springclouddev.system.wrapper.RoleWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author zhaobohao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色", tags = "角色")
public class RoleController extends AbstractController {

	private IRoleService roleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入role")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> role, SystemUser systemUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!systemUser.getTenantId().equals(ToolConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, systemUser.getTenantId()) : queryWrapper);
		return R.data(RoleWrapper.build().listNodeVO(list));
	}
	/**
	 * 列表
	 */
	@GetMapping("/list/page")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "parentId", value = "父id", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public R<IPage<Role>> list(@ApiIgnore @RequestParam Map<String, Object> role, Query query, SystemUser systemUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		IPage<Role> pages = roleService.page(Condition.getPage(query), (!systemUser.getTenantId().equals(ToolConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, systemUser.getTenantId()) : queryWrapper);
		return R.data(pages);
	}
	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<RoleVO>> tree(String parentId, SystemUser systemUser) {
		List<RoleVO> tree = roleService.tree(parentId);
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R submit(@Valid @RequestBody Role role, SystemUser user) {
		if (Func.isEmpty(role.getId()) && Func.isEmpty(role.getTenantId())) {
			role.setTenantId(user.getTenantId());
		}
		if (roleService.saveOrUpdate(role)) {
			return R.data(role);
		} else {
			return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, role, ToolConstant.DEFAULT_FAILURE_MESSAGE);
		}
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {

		return R.status(roleService.removeByIds(Func.toIntList(ids)));
	}

	/**
	 * 设置菜单权限
	 *
	 * @param roleIds
	 * @param menuIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R grant(@ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds,
				   @ApiParam(value = "menuId集合", required = true) @RequestParam String menuIds) {
		boolean temp = roleService.grant(Func.toIntList(roleIds), Func.toIntList(menuIds));
		return R.status(temp);
	}

}
