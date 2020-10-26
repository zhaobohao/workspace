package org.springclouddev.crm.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import org.springclouddev.crm.admin.common.AdminCodeEnum;
import org.springclouddev.crm.admin.api.bo.*;
import org.springclouddev.crm.admin.api.entity.AdminConfig;
import org.springclouddev.crm.admin.api.entity.AdminUser;
import org.springclouddev.crm.admin.api.entity.AdminUserConfig;
import org.springclouddev.crm.admin.api.vo.AdminUserVO;
import org.springclouddev.crm.admin.api.vo.HrmSimpleUserVO;
import org.springclouddev.crm.admin.service.*;
import org.springclouddev.crm.core.common.ApiExplain;
import org.springclouddev.crm.core.common.ParamAspect;
import org.springclouddev.crm.core.common.R;
import org.springclouddev.crm.core.common.Result;
import org.springclouddev.crm.core.entity.BasePage;
import org.springclouddev.crm.core.entity.UserInfo;
import org.springclouddev.crm.core.exception.NoLoginException;
import org.springclouddev.crm.admin.api.entity.SimpleUser;
import org.springclouddev.crm.core.feign.email.EmailService;
import org.springclouddev.crm.core.servlet.ApplicationContextHolder;
import org.springclouddev.crm.core.servlet.upload.UploadEntity;
import org.springclouddev.crm.core.utils.UserCacheUtil;
import org.springclouddev.crm.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/adminUser")
@Api(tags = "员工管理相关接口")
@Slf4j
public class AdminUserController {

    @Autowired
    private IAdminUserService adminUserService;

    @Autowired
    private IAdminUserConfigService adminUserConfigService;

    @Autowired
    private IAdminFileService adminFileService;

    @RequestMapping("/findByUsername")
    @ApiOperation(value = "通过name查询用户", httpMethod = "POST")
    public Result<List<Map<String, Object>>> findByUsername(String username) {
        List<Map<String, Object>> userInfoList = adminUserService.findByUsername(username);
        return Result.ok(userInfoList);
    }

    @ApiOperation("通过条件分页查询员工列表")
    @PostMapping("/queryUserList")
    public Result<BasePage<AdminUserVO>> queryUserList(@RequestBody AdminUserBO adminUserBO) {
        return R.ok(adminUserService.queryUserList(adminUserBO));
    }

    @ApiExplain("通过条件分页查询员工列表")
    @PostMapping("/queryAllUserList")
    public Result<List<Long>> queryAllUserList() {
        List<AdminUserVO> adminUserBOList = adminUserService.queryUserList(null).getList();
        return R.ok(adminUserBOList.stream().map(AdminUserVO::getUserId).collect(Collectors.toList()));
    }

    @PostMapping("/setUser")
    @ApiOperation("修改用户")
    public Result setUser(@RequestBody AdminUserVO adminUserVO) {
        adminUserService.setUser(adminUserVO);
        return R.ok();
    }

    @PostMapping("/addUser")
    @ApiOperation("新增用户")
    public Result addUser(@RequestBody AdminUserVO adminUserVO) {
        adminUserService.addUser(adminUserVO);
        return R.ok();
    }

    @PostMapping("/usernameEdit")
    @ApiOperation("重置登录账号")
    public Result<Integer> usernameEdit(@RequestParam("id") Integer id, @RequestParam("username") String username, @RequestParam("password") String password) {
        Integer integer = adminUserService.usernameEdit(id, username, password);
        return R.ok(integer);
    }

    @PostMapping("/excelImport")
    @ApiOperation("excel导入员工")
    public Result<JSONObject> excelImport(@RequestParam("file") MultipartFile file) {
        JSONObject object = adminUserService.excelImport(file);
        return R.ok(object);
    }

    @PostMapping("/downExcel")
    @ApiOperation("excel下载错误数据")
    public void downExcel(@RequestParam("token") String token, HttpServletResponse response) {
        String path = FileUtil.getTmpDirPath() + "/" + token;
        if (FileUtil.exist(path)) {
            File file = FileUtil.file(path);
            final String fileName = file.getName();
            final String contentType = ObjectUtil.defaultIfNull(FileUtil.getMimeType(fileName), "application/octet-stream");
            BufferedInputStream in = null;
            try {
                in = FileUtil.getInputStream(file);
                ServletUtil.write(response, in, contentType, "import_error.xls");
            } finally {
                IoUtil.close(in);
            }
            FileUtil.del(path);
        }
    }

    @PostMapping("/hrmAddUser")
    @ApiOperation("从人力资源添加员工")
    public Result hrmAddUser(@RequestBody HrmAddUserBO hrmAddUserBO) {
        adminUserService.hrmAddUser(hrmAddUserBO);
        return R.ok();
    }

    @PostMapping("/setUserStatus")
    @ApiOperation("禁用启用")
    public Result setUserStatus(@RequestBody AdminUserStatusBO adminUserStatusBO) {
        adminUserService.setUserStatus(adminUserStatusBO);
        return R.ok();
    }

    @PostMapping("/resetPassword")
    @ApiOperation("重置密码")
    public Result resetPassword(@RequestBody AdminUserStatusBO adminUserStatusBO) {
        adminUserService.resetPassword(adminUserStatusBO);
        return R.ok();
    }

    @PostMapping("/updateImg")
    @ApiOperation("修改头像")
    public Result updateImg(@RequestParam("file") MultipartFile file) throws IOException {
        UploadEntity img = adminFileService.upload(file, null, "img", "0");
        AdminUser byId = adminUserService.getById(UserUtil.getUserId());
        byId.setImg(img.getUrl());
        adminUserService.updateById(byId);
        return R.ok();
    }

    @PostMapping("/updatePassword")
    @ApiOperation("修改登录密码")
    public Result updatePassword(@RequestParam("oldPwd") String oldPass, @RequestParam("newPwd") String newPass) {
        AdminUser adminUser = adminUserService.getById(UserUtil.getUserId());
        if (!UserUtil.verify(adminUser.getUsername() + oldPass, adminUser.getSalt(), adminUser.getPassword())) {
            return R.error(AdminCodeEnum.ADMIN_PASSWORD_ERROR);
        }
        adminUser.setPassword(newPass);
        return updateUser(adminUser);
    }

    @PostMapping("/updateUser")
    @ApiOperation("修改用户信息")
    public Result updateUser(@RequestBody AdminUser adminUser) {
        adminUserService.updateUser(adminUser);
        return R.ok();
    }

    @Autowired
    private IAdminDeptService deptService;

    @PostMapping("/queryLoginUser")
    @ApiOperation("查询当前登录用户")
    public Result<AdminUserVO> queryLoginUser() {
        String name = "readNotice";
        AdminUser user = adminUserService.getById(UserUtil.getUser().getUserId());
        if (user == null) {
            throw new NoLoginException();
        }
        AdminUserVO adminUser = BeanUtil.copyProperties(user, AdminUserVO.class);
        adminUser.setIsAdmin(UserUtil.isAdmin());
        AdminUserConfig userConfig = adminUserConfigService.queryUserConfigByName(name);
        adminUser.setIsReadNotice(userConfig != null ? userConfig.getStatus() : 0);
        adminUser.setPassword(null);
        String deptName = deptService.getNameByDeptId(adminUser.getDeptId());
        adminUser.setDeptName(deptName);
        adminUser.setParentName(UserCacheUtil.getUserName(adminUser.getParentId()));
        AdminConfig config = ApplicationContextHolder.getBean(IAdminConfigService.class).queryConfigByName("email");
        if (config != null && config.getStatus() == 1) {
            Integer data = ApplicationContextHolder.getBean(EmailService.class).getEmailId(adminUser.getUserId()).getData();
            adminUser.setEmailId(data);
        }
        return R.ok(adminUser);
    }

    @RequestMapping("/queryUserRoleIds")
    @ApiExplain("查询用户角色列表")
    public Result<List<Integer>> queryUserRoleIds(@RequestParam("userId") @NotNull Long userId) {
        return R.ok(adminUserService.queryUserRoleIds(userId));
    }

    @RequestMapping("/queryListName")
    @ApiExplain("查询通讯录")
    public Result queryListName(@RequestBody UserBookBO userBookBO) {
        return R.ok(adminUserService.queryListName(userBookBO));
    }

    @RequestMapping("/attention")
    @ApiExplain("切换关注状态")
    public Result attention(@RequestParam("userId") Long userId) {
        adminUserService.attention(userId);
        return R.ok();
    }

    @RequestMapping("/getNameByUserId")
    @ApiExplain("根据用户ID获取用户名称")
    public Result getNameByUserId(@NotNull Long userId) {
        return R.ok(adminUserService.getNameByUserId(userId));
    }

    @RequestMapping("/queryChildUserId")
    @ApiExplain("根据用户ID下的子用户")
    public Result<List<Long>> queryChildUserId(@NotNull Long userId) {
        List<Long> longList = adminUserService.queryChildUserId(userId);
        return R.ok(longList);
    }

    @RequestMapping("/queryUserInfo")
    @ApiOperation("查询用户信息")
    public Result<AdminUser> queryUserInfo(@RequestParam("userId") Long userId) {
        AdminUser byId = adminUserService.getById(userId);
        String nameByDeptId = ApplicationContextHolder.getBean(IAdminDeptService.class).getNameByDeptId(byId.getDeptId());
        byId.setDeptName(nameByDeptId);
        byId.setSalt(null);
        byId.setPassword(null);
        return R.ok(byId);
    }

    @RequestMapping("/queryInfoByUserId")
    @ApiExplain("根据用户ID获取用户")
    public Result<UserInfo> queryInfoByUserId(@NotNull Long userId) {
        AdminUser byId = adminUserService.getById(userId);
        UserInfo userInfo = null;
        if (byId != null && byId.getDeptId() != null) {
            userInfo = BeanUtil.copyProperties(byId, UserInfo.class);
            String nameByDeptId = ApplicationContextHolder.getBean(IAdminDeptService.class).getNameByDeptId(byId.getDeptId());
            userInfo.setDeptName(nameByDeptId);
        }
        return R.ok(userInfo);
    }

    @PostMapping("/queryUserByIds")
    @ApiExplain("根据用户ID获取用户")
    public Result<List<SimpleUser>> queryUserByIds(@RequestBody List<Long> ids) {
        List<SimpleUser> simpleUsers = adminUserService.queryUserByIds(ids);
        return R.ok(simpleUsers);
    }

    @PostMapping("/queryUserById")
    @ApiExplain("根据用户ID获取用户")
    public Result<SimpleUser> queryUserById(@RequestParam("userId") Long userId) {
        AdminUser adminUser = adminUserService.getById(userId);
        return R.ok(BeanUtil.copyProperties(adminUser, SimpleUser.class));
    }

    @PostMapping("/queryUserByDeptIds")
    @ApiExplain("根据部门ID获取用户ids")
    public Result<List<Long>> queryUserByDeptIds(@RequestBody List<Integer> ids) {
        List<Long> userIds = adminUserService.queryUserByDeptIds(ids);
        return R.ok(userIds);
    }

    @PostMapping("/readNotice")
    @ApiOperation("设置更新日志为已读")
    public Result readNotice() {
        Long userId = UserUtil.getUserId();
        String name = "readNotice";
        Integer count = adminUserConfigService.lambdaQuery().eq(AdminUserConfig::getUserId, userId).eq(AdminUserConfig::getName, name).count();
        if (count > 1) {
            adminUserConfigService.lambdaUpdate().set(AdminUserConfig::getStatus, 1).eq(AdminUserConfig::getUserId, userId).eq(AdminUserConfig::getName, name).update();
        } else {
            AdminUserConfig adminUserConfig = new AdminUserConfig();
            adminUserConfig.setValue("");
            adminUserConfig.setName(name);
            adminUserConfig.setUserId(userId);
            adminUserConfig.setStatus(1);
            adminUserConfig.setDescription("升级日志阅读状态");
            adminUserConfigService.save(adminUserConfig);
        }
        return R.ok();
    }


    @PostMapping("/queryAuthUserList")
    @ApiOperation("查询权限下用户")
    public Result<List<SimpleUser>> queryAuthUserList() {
        List<SimpleUser> userList = new ArrayList<>();
        if (UserUtil.isAdmin()) {
            userList.addAll(adminUserService.list().stream().map(user -> BeanUtil.copyProperties(user, SimpleUser.class)).collect(Collectors.toList()));
        } else {
            List<Long> childUserId = adminUserService.queryChildUserId(UserUtil.getUserId());
            userList.addAll(adminUserService.queryUserByIds(childUserId));
        }
        return R.ok(userList);
    }

    @PostMapping("/queryDeptUserList/{deptId}")
    @ApiOperation("查询部门用户列表(表单使用)")
    public Result<DeptUserListVO> queryDeptUserList(@PathVariable Integer deptId) {
        DeptUserListVO deptUserListVO = adminUserService.queryDeptUserList(deptId);
        return Result.ok(deptUserListVO);
    }

    @PostMapping("/queryDeptUserListByHrm")
    @ApiOperation("查询部门用户列表(hrm添加员工使用)")
    public Result<Set<HrmSimpleUserVO>> queryDeptUserListByHrm(@RequestBody DeptUserListByHrmBO deptUserListByHrmBO) {
        Set<HrmSimpleUserVO> userList = adminUserService.queryDeptUserListByHrm(deptUserListByHrmBO);
        return Result.ok(userList);
    }

    @PostMapping("/queryUserIdByRealName")
    @ApiOperation("查询用户id根据真实姓名")
    public Result<List<Long>> queryUserIdByRealName(@RequestParam("realNames") List<String> realNames) {
        List<Long> userIdList = adminUserService.queryUserIdByRealName(realNames);
        return Result.ok(userIdList);
    }

    @PostMapping("/queryLoginUserInfo")
    @ApiExplain("模拟查询登陆用户信息")
    public Result<UserInfo> queryLoginUserInfo(@RequestParam("userId") Long userId) {
        UserInfo userInfo = adminUserService.queryLoginUserInfo(userId);
        return Result.ok(userInfo);
    }

    @PostMapping("/querySystemStatus")
    @ApiOperation("查询当前系统初始状态")
    @ParamAspect
    public Result<Integer> querySystemStatus() {
        Integer status = adminUserService.querySystemStatus();
        return R.ok(status);
    }

    @PostMapping("/initUser")
    @ApiOperation("初始化系统用户")
    @ParamAspect
    public Result initUser(@Validated @RequestBody SystemUserBO systemUserBO){
        adminUserService.initUser(systemUserBO);
        return R.ok();
    }
}

