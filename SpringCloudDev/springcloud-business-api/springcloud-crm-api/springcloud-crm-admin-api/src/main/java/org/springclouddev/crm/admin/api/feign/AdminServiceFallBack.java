package org.springclouddev.crm.admin.api.feign;

import com.alibaba.fastjson.JSONObject;
import org.springclouddev.crm.admin.api.entity.AdminConfig;
import org.springclouddev.crm.admin.api.entity.AdminMessage;
import org.springclouddev.crm.admin.api.entity.SimpleDept;
import org.springclouddev.crm.admin.api.entity.SimpleUser;
import org.springclouddev.crm.core.common.R;
import org.springclouddev.crm.core.common.Result;
import org.springclouddev.crm.core.common.SystemCodeEnum;
import org.springclouddev.crm.core.entity.UserInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminServiceFallBack implements AdminService {

    /**
     * 根据用户ID获取用户名
     *
     * @param userId 用户ID
     * @return data
     */
    @Override
    public Result<UserInfo> getUserInfo(Long userId) {
        return R.ok(null);
    }

    /**
     * 根据用户ID获取用户名
     * @param userId 用户ID
     * @return data
     */
    @Override
    public Result<String> queryUserName(Long userId) {
        return Result.ok("");
    }

    /**
     *
     * 根据部门ID获取部门名称
     * @param deptId 部门ID
     * @return 结果信息
     */
    @Override
    public Result<String> queryDeptName(Integer deptId) {
        return Result.ok("");
    }

    /**
     * 查询部门下属部门
     *
     * @param deptId 上级ID
     * @return data
     */
    @Override
    public Result<List<Integer>> queryChildDeptId(Integer deptId) {
        return R.ok(new ArrayList<>());
    }

    /**
     * 查询该用户下级的用户
     *
     * @param userId 用户ID 0代表全部
     * @return data
     */
    @Override
    public Result<List<Long>> queryChildUserId(Long userId) {
        return R.ok(new ArrayList<>());
    }

    /**
     * 查询企业所有用户
     *
     * @return data
     */
    @Override
    public Result<List<Long>> queryUserList() {
        return R.ok(new ArrayList<>());
    }

    /**
     * 根据名称查询系统配置
     *
     * @param name 名称
     * @return data
     */
    @Override
    public Result<List<AdminConfig>> queryConfigByName(String name) {
        return R.ok(new ArrayList<>());
    }

    /**
     * 根据名称查询系统配置
     *
     * @param name 名称
     * @return data
     */
    @Override
    public Result<AdminConfig> queryFirstConfigByName(String name) {
        return R.ok((AdminConfig) null);
    }

    /**
     * 根据ids查询用户信息
     *
     * @param ids id列表
     * @return data
     */
    @Override
    public Result<List<SimpleUser>> queryUserByIds(Collection<Long> ids) {
        return R.ok(new ArrayList<>());
    }

    /**
     * 根据ids查询用户信息
     *
     * @param userId id
     * @return data
     */
    @Override
    public Result<SimpleUser> queryUserById(Long userId) {
        return R.ok(null);
    }

    /**
     * 根据ids查询部门信息
     *
     * @param ids id列表
     * @return data
     */
    @Override
    public Result<List<SimpleDept>> queryDeptByIds(Collection<Integer> ids) {
        return R.ok(new ArrayList<>());
    }

    /**
     * 根据ids查询部门信息
     *
     * @param ids id列表
     * @return data
     */
    @Override
    public Result<List<Long>> queryUserByDeptIds(Collection<Integer> ids) {
        return R.ok(new ArrayList<>());
    }

    @Override
    public Result<Integer> queryDataType(Long userId, Integer menuId) {
        return R.ok(1);
    }

    @Override
    public Result<Integer> queryMaxDataType(Long userId, Integer menuId) {
        return R.ok(1);
    }

    /**
     * 查询权限内用户
     * @param userId 用户ID
     * @param realm 菜单标识
     * @return 权限
     */
    @Override
    public Result<List<Long>> queryUserByAuth(Long userId, String realm) {
        return R.ok(new ArrayList<>());
    }

    @Override
    public Result<Integer> queryWorkRole(Integer label) {
        return R.ok(1);
    }

    /**
     * 根据角色类型查询角色
     *
     * @param roleType 角色类型
     * @return type
     */
    @Override
    public Result<List<Integer>> queryRoleByRoleType(Integer roleType) {
        return R.ok(new ArrayList<>());
    }

    @Override
    public Result updateAdminConfig(AdminConfig adminConfig) {
        return R.ok();
    }

    @Override
    public Result<JSONObject> auth() {
        return R.ok(new JSONObject());
    }

    @Override
    public Result saveOrUpdateMessage(AdminMessage message) {
        return R.error(SystemCodeEnum.SYSTEM_SERVER_ERROR);
    }

    @Override
    public Result<AdminMessage> getMessageById(Long messageId) {
        return R.ok((AdminMessage) null);
    }

    @Override
    public Result<AdminConfig> queryFirstConfigByNameAndValue(String name, String value) {
        return R.ok(new AdminConfig().setStatus(0));
    }

    @Override
    public Result<Integer> queryMenuId(String realm1, String realm2, String realm3) {
        return R.ok(0);
    }

    @Override
    public Result<List<Long>> queryUserIdByRealName(List<String> realNames) {
        return Result.ok(new ArrayList<>());
    }

    @Override
    public Result<UserInfo> queryLoginUserInfo(Long userId) {
        return Result.ok(new UserInfo());
    }

    @Override
    public Result<Integer> querySystemStatus() {
        return Result.ok(0);
    }
}
