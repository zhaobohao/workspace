package org.springclouddev.crm.admin.api.feign;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springclouddev.crm.admin.api.entity.AdminConfig;
import org.springclouddev.crm.admin.api.entity.AdminMessage;
import org.springclouddev.crm.admin.api.entity.SimpleDept;
import org.springclouddev.crm.admin.api.entity.SimpleUser;
import org.springclouddev.crm.core.common.ApiExplain;
import org.springclouddev.crm.core.common.Result;
import org.springclouddev.crm.core.entity.UserInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author zhangzhwei
 * 系统管理模块的一些请求
 */
@FeignClient(name = "admin", contextId = "admin")
public interface AdminService {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return data
     */
    @RequestMapping(value = "/adminUser/queryInfoByUserId")
    public Result<UserInfo> getUserInfo(@RequestParam("userId") Long userId);

    /**
     * 根据用户ID获取用户名
     *
     * @param userId 用户ID
     * @return data
     */
    @RequestMapping(value = "/adminUser/getNameByUserId")
    @Cacheable(value = "crm-user")
    public Result<String> queryUserName(@RequestParam("userId") Long userId);


    /**
     * 根据部门ID获取部门名称
     *
     * @param deptId 部门ID
     * @return 结果信息
     */
    @RequestMapping(value = "/adminDept/getNameByDeptId")
    @Cacheable(value = "crm-user")
    public Result<String> queryDeptName(@RequestParam("deptId") Integer deptId);

    /**
     * 查询部门下属部门
     *
     * @param deptId 上级ID
     * @return data
     */
    @RequestMapping(value = "/adminDept/queryChildDeptId")
    public Result<List<Integer>> queryChildDeptId(@RequestParam("deptId") Integer deptId);

    /**
     * 查询该用户下级的用户
     *
     * @param userId 用户ID 0代表全部
     * @return data
     */
    @RequestMapping(value = "/adminUser/queryChildUserId")
    public Result<List<Long>> queryChildUserId(@RequestParam("userId") Long userId);

    /**
     * 查询企业所有用户
     *
     * @return data
     */
    @PostMapping(value = "/adminUser/queryAllUserList")
    public Result<List<Long>> queryUserList();

    /**
     * 根据名称查询系统配置
     *
     * @param name 名称
     * @return data
     */
    @PostMapping(value = "/adminConfig/queryConfigByName")
    public Result<List<AdminConfig>> queryConfigByName(@RequestParam("name") String name);

    /**
     * 根据名称查询系统配置
     *
     * @param name 名称
     * @return data
     */
    @RequestMapping(value = "/adminConfig/queryFirstConfigByName")
    public Result<AdminConfig> queryFirstConfigByName(@RequestParam("name") String name);


    /**
     * 根据ids查询用户信息
     *
     * @param ids id列表
     * @return data
     */
    @PostMapping(value = "/adminUser/queryUserByIds")
    public Result<List<SimpleUser>> queryUserByIds(@RequestBody Collection<Long> ids);

    /**
     * 根据ids查询用户信息
     *
     * @param userId id
     * @return data
     */
    @PostMapping(value = "/adminUser/queryUserById")
    public Result<SimpleUser> queryUserById(@RequestParam("userId") Long userId);

    /**
     * 根据ids查询部门信息
     *
     * @param ids id列表
     * @return data
     */
    @PostMapping(value = "/adminDept/queryDeptByIds")
    public Result<List<SimpleDept>> queryDeptByIds(@RequestBody Collection<Integer> ids);

    /**
     * 根据ids查询部门下的用户
     *
     * @param ids id列表
     * @return data
     */
    @PostMapping(value = "/adminUser/queryUserByDeptIds")
    public Result<List<Long>> queryUserByDeptIds(@RequestBody Collection<Integer> ids);

    /**
     * 查询数据权限
     *
     * @param userId 用户ID
     * @param menuId 菜单ID
     * @return 权限
     */
    @PostMapping(value = "/adminRole/queryDataType")
    public Result<Integer> queryDataType(@RequestParam("userId") Long userId, @RequestParam("menuId") Integer menuId);


    /**
     * 查询数据权限
     *
     * @param userId 用户ID
     * @param menuId 菜单ID
     * @return 权限
     */
    @PostMapping(value = "/adminRole/queryMaxDataType")
    public Result<Integer> queryMaxDataType(@RequestParam("userId") Long userId, @RequestParam("menuId") Integer menuId);

    /**
     * 查询权限内用户
     *
     * @param userId 用户ID
     * @param realm  菜单标识
     * @return 权限
     */
    @PostMapping(value = "/adminRole/queryUserByAuth")
    public Result<List<Long>> queryUserByAuth(@RequestParam("userId") Long userId, @RequestParam("realm") String realm);

    @PostMapping(value = "/adminRole/queryWorkRole")
    public Result<Integer> queryWorkRole(@RequestParam("label") Integer label);

    /**
     * 根据角色类型查询角色
     *
     * @param type 角色类型
     * @return type
     */
    @PostMapping(value = "/adminRole/queryRoleByRoleType")
    public Result<List<Integer>> queryRoleByRoleType(@RequestParam("type") Integer type);

    /**
     * 修改系統配置
     *
     * @param adminConfig 配置实体
     * @return data
     */
    @PostMapping(value = "/adminConfig/updateAdminConfig")
    public Result updateAdminConfig(@RequestBody AdminConfig adminConfig);

    @PostMapping("/adminRole/auth")
    @ApiOperation("角色权限")
    Result<JSONObject> auth();

    @PostMapping("/adminMessage/saveOrUpdateMessage")
    public Result<Long> saveOrUpdateMessage(@RequestBody AdminMessage message);

    @PostMapping("/adminMessage/getById/{messageId}")
    public Result<AdminMessage> getMessageById(@PathVariable Long messageId);

    @ApiExplain(value = "查询config配置")
    @RequestMapping("/adminConfig/queryFirstConfigByNameAndValue")
    public Result<AdminConfig> queryFirstConfigByNameAndValue(@RequestParam("name") String name,
                                                              @RequestParam("value") String value);

    @RequestMapping("/adminMenu/queryMenuId")
    public Result<Integer> queryMenuId(@RequestParam("realm1") String realm1, @RequestParam("realm2") String realm2,
                                       @RequestParam("realm3") String realm3);

    @PostMapping("/adminUser/queryUserIdByRealName")
    @ApiOperation("查询用户id根据真实姓名")
    public Result<List<Long>> queryUserIdByRealName(@RequestParam("realNames") List<String> realNames);

    @PostMapping("/adminUser/queryLoginUserInfo")
    @ApiExplain("模拟查询登陆用户信息")
    Result<UserInfo> queryLoginUserInfo(@RequestParam("userId") Long userId);

    @PostMapping("/adminUser/querySystemStatus")
    @ApiExplain("查询当前系统初始状态")
    public Result<Integer> querySystemStatus();
}
