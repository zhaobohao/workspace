package org.springclouddev.crm.admin.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.springclouddev.crm.admin.common.AdminRoleTypeEnum;
import org.springclouddev.crm.admin.api.entity.AdminRole;
import org.springclouddev.crm.admin.api.vo.AdminRoleVO;
import org.springclouddev.crm.admin.service.IAdminRoleService;
import org.springclouddev.crm.core.common.ApiExplain;
import org.springclouddev.crm.core.common.Const;
import org.springclouddev.crm.core.common.R;
import org.springclouddev.crm.core.common.Result;
import org.springclouddev.crm.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/adminRole")
@Api(tags = "角色模块")
public class AdminRoleController {
    @Autowired
    private IAdminRoleService adminRoleService;

    @PostMapping("/auth")
    @ApiOperation("角色权限")
    public Result<JSONObject> auth() {
        JSONObject object = adminRoleService.auth(UserUtil.getUserId());
        return R.ok(object);
    }

    @PostMapping("/queryNoAuthMenu")
    @ApiOperation("获取未授权的菜单")
    public Result<List<String>> queryNoAuthMenu(@RequestParam("userId") @NotNull Long userId) {
        return R.ok(adminRoleService.queryNoAuthMenu(userId));
    }

    @PostMapping("/getAllRoleList")
    @ApiOperation("全局角色查询")
    public Result<List<AdminRoleVO>> getAllRoleList() {
        List<AdminRoleVO> allRoleList = adminRoleService.getAllRoleList();
        return R.ok(allRoleList);
    }

    @PostMapping("/getRoleTypeList")
    @ApiOperation("获取角色类型列表")
    public Result<List<Map<String, Object>>> getRoleTypeList() {
        List<Map<String, Object>> data = new ArrayList<>(6);
        data.add(new JSONObject().fluentPut("name", "系统管理角色").fluentPut("roleType", 1));
        data.add(new JSONObject().fluentPut("name", "办公管理角色").fluentPut("roleType", 7));
        data.add(new JSONObject().fluentPut("name", "客户管理角色").fluentPut("roleType", 2));
        data.add(new JSONObject().fluentPut("name", "项目管理角色").fluentPut("roleType", 8));
        return R.ok(data);
    }

    @PostMapping("/getRoleByType/{type}")
    @ApiOperation("通过角色类型查询角色")
    public Result<List<AdminRole>> getRoleByType(@PathVariable("type") Integer type) {
        List<AdminRole> roleByType = adminRoleService.getRoleByType(AdminRoleTypeEnum.parse(type));
        return R.ok(roleByType);
    }

    @PostMapping("/queryRoleByRoleType")
    @ApiExplain("通过角色类型查询角色")
    public Result<List<Integer>> queryRoleByRoleType(@RequestParam("type") Integer type) {
        List<AdminRole> recordList = adminRoleService.lambdaQuery().select(AdminRole::getRoleId).eq(AdminRole::getRoleType, type).list();
        return R.ok(recordList.stream().map(AdminRole::getRoleId).collect(Collectors.toList()));
    }

    @PostMapping("/queryDataType")
    @ApiExplain("查询数据权限")
    public Result<Integer> queryDataType(@RequestParam("userId") Long userId, @RequestParam("menuId") Integer menuId) {
        Integer dataType = adminRoleService.queryDataType(userId, menuId);
        return R.ok(dataType);
    }

    @PostMapping("/queryMaxDataType")
    @ApiExplain("查询数据权限")
    public Result<Integer> queryMaxDataType(@RequestParam("userId") Long userId, @RequestParam("menuId") Integer menuId) {
        Integer dataType = adminRoleService.queryMaxDataType(userId, menuId);
        return R.ok(dataType);
    }
    @PostMapping("/queryUserByAuth")
    @ApiExplain("查询数据权限")
    public Result<List<Long>> queryUserByAuth(@RequestParam("userId") Long userId, @RequestParam("realm") String realm) {
        List<Long> longs = adminRoleService.queryUserByAuth(userId, realm);
        return R.ok(longs);
    }

    @PostMapping("/add")
    @ApiOperation("添加角色")
    public Result add(@RequestBody AdminRole adminRole){
        adminRoleService.add(adminRole);
        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改角色")
    public Result update(@RequestBody AdminRole adminRole){
        adminRoleService.add(adminRole);
        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除角色")
    public Result delete(@RequestParam("roleId") Integer roleId){
        adminRoleService.delete(roleId);
        return R.ok();
    }

    @PostMapping("/copy")
    @ApiOperation("复制角色")
    public Result copy(@RequestParam("roleId") Integer roleId){
        adminRoleService.copy(roleId);
        return R.ok();
    }

    @PostMapping("/relatedUser")
    @ApiOperation("角色关联员工")
    public Result relatedUser(@RequestParam("userIds") String userIds, @RequestParam("roleIds") String roleIds){
        adminRoleService.relatedUser(StrUtil.splitTrim(userIds, Const.SEPARATOR), StrUtil.splitTrim(roleIds, Const.SEPARATOR));
        return R.ok();
    }

    @PostMapping("/unbindingUser")
    @ApiOperation("取消角色关联员工")
    public Result unbindingUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Integer roleId){
        adminRoleService.unbindingUser(userId,roleId);
        return R.ok();
    }
    @PostMapping("/updateRoleMenu")
    @ApiOperation("保存角色菜单关系")
    public Result updateRoleMenu(@RequestBody AdminRole adminRole){
        adminRoleService.updateRoleMenu(adminRole);
        return R.ok();
    }

    @PostMapping(value = "/queryWorkRole")
    @ApiExplain("查询项目管理角色")
    public Result<Integer> queryWorkRole(@RequestParam("label") Integer label){
        Integer role = adminRoleService.queryWorkRole(label);
        return R.ok(role);
    }

    @PostMapping(value = "/setWorkRole")
    @ApiExplain("设置项目管理角色")
    public Result setWorkRole(@RequestBody JSONObject object){
        adminRoleService.setWorkRole(object);
        return R.ok();
    }

    @PostMapping(value = "/deleteWorkRole")
    @ApiExplain("删除项目管理角色")
    public Result deleteWorkRole(@RequestParam("roleId") Integer roleId){
        adminRoleService.deleteWorkRole(roleId);
        return R.ok();
    }

    @PostMapping(value = "/queryProjectRoleList")
    @ApiOperation("查询项目管理角色列表")
    public Result<List<AdminRole>> queryProjectRoleList(){
        List<AdminRole> adminRoles = adminRoleService.queryProjectRoleList();
        return R.ok(adminRoles);
    }

    @PostMapping(value ="/queryWorkRoleList")
    @ApiOperation("查询项目管理角色列表")
    public Result<List<AdminRole>> queryWorkRoleList(){
        List<AdminRole> adminRoles = adminRoleService.queryRoleList();
        return R.ok(adminRoles);
    }
}

