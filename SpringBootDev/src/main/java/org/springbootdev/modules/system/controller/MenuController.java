
package org.springbootdev.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springbootdev.core.boot.ctrl.AbstractController;
import org.springbootdev.core.launch.constant.AppConstant;
import org.springbootdev.core.mp.support.Condition;
import org.springbootdev.core.mp.support.Query;
import org.springbootdev.core.secure.SystemUser;
import org.springbootdev.core.secure.annotation.PreAuth;
import org.springbootdev.core.tool.api.R;
import org.springbootdev.core.tool.constant.RoleConstant;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springbootdev.core.tool.support.Kv;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.modules.system.entity.Menu;
import org.springbootdev.modules.system.service.IMenuService;
import org.springbootdev.modules.system.vo.MenuVO;
import org.springbootdev.modules.system.wrapper.MenuWrapper;
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
@RequestMapping("/"+AppConstant.APPLICATION_SYSTEM_NAME +"/menu")
@Api(value = "菜单", tags = "菜单")
public class MenuController extends AbstractController {

    private IMenuService menuService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @PreAuth(RoleConstant.HAS_ROLE_ADMIN)
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入menu")
    public R<MenuVO> detail(Menu menu) {
        Menu detail = menuService.getOne(Condition.getQueryWrapper(menu));
        return R.data(MenuWrapper.build().entityVO(detail));
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
    })
    @PreAuth(RoleConstant.HAS_ROLE_ADMIN)
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入menu")
    public R<List<MenuVO>> list(@ApiIgnore @RequestParam Map<String, Object> menu) {
        @SuppressWarnings("unchecked")
        List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().orderByAsc(Menu::getSort));
        return R.data(MenuWrapper.build().listNodeVO(list));
    }
    /**
     * 列表
     */
    @GetMapping("/list/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "父id", paramType = "query", dataType = "string")
    })
    @PreAuth(RoleConstant.HAS_ROLE_ADMIN)
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入menu")
    public R<IPage<Menu>>  list(@ApiIgnore @RequestParam Map<String, Object> menu, Query query) {

        QueryWrapper<Menu> queryWrapper = Condition.getQueryWrapper(menu, Menu.class);
        IPage<Menu> pages = menuService.page(Condition.getPage(query),queryWrapper.lambda().orderByAsc(Menu::getSort));
        return R.data(pages);
    }
    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @PreAuth(RoleConstant.HAS_ROLE_ADMIN)
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增或修改", notes = "传入menu")
    public R submit(@Valid @RequestBody Menu menu) {
		if (menuService.submit(menu)) {
            return R.data(menu);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, menu, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @PreAuth(RoleConstant.HAS_ROLE_ADMIN)
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(menuService.removeByIds(Func.toIntList(ids)));
    }

    /**
     * 前端菜单数据
     */
    @GetMapping("/routes")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
    public R<List<MenuVO>> routes(SystemUser user) {
        List<MenuVO> list = menuService.routes(user.getRoleId());
        return R.data(list);
    }

    /**
     * 前端按钮数据
     */
    @GetMapping("/buttons")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "前端按钮数据", notes = "前端按钮数据")
    public R<List<MenuVO>> buttons(SystemUser user) {
        List<MenuVO> list = menuService.buttons(user.getRoleId());
        return R.data(list);
    }

    /**
     * 获取菜单树形结构
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<MenuVO>> tree(String parentId) {
        List<MenuVO> tree = menuService.tree(parentId);
        return R.data(tree);
    }

    /**
     * 获取权限分配树形结构
     */
    @GetMapping("/grant-tree")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "权限分配树形结构", notes = "权限分配树形结构")
    public R<List<MenuVO>> grantTree(SystemUser user) {
        return R.data(menuService.grantTree(user));
    }

    /**
     * 获取权限分配树形结构
     */
    @GetMapping("/role-tree-keys")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
    public R<List<String>> roleTreeKeys(String roleIds) {
        return R.data(menuService.roleTreeKeys(roleIds));
    }

    /**
     * 获取配置的角色权限
     */
    @GetMapping("auth-routes")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "菜单的角色权限")
    public R<List<Kv>> authRoutes(SystemUser user) {
        return R.data(menuService.authRoutes(user));
    }

}
