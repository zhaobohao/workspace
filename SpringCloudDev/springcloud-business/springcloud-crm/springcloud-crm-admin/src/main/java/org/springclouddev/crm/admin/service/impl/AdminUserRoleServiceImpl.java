package org.springclouddev.crm.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springclouddev.crm.admin.api.entity.AdminUserRole;
import org.springclouddev.crm.admin.mapper.AdminUserRoleMapper;
import org.springclouddev.crm.admin.service.IAdminUserRoleService;
import org.springclouddev.crm.core.common.Const;
import org.springclouddev.crm.core.servlet.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户角色对应关系表 服务实现类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
@Service
public class AdminUserRoleServiceImpl extends BaseServiceImpl<AdminUserRoleMapper, AdminUserRole> implements IAdminUserRoleService {

    /**
     * 通过userID删除该用户的所有
     *
     * @param userId   用户ID
     * @param isRemove 是否删除原有角色
     * @param roleIds   角色列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveByUserId(Long userId, boolean isRemove, List<String> roleIds) {
        if (isRemove) {
            QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            remove(queryWrapper);
        }
        List<AdminUserRole> adminUserRoleList = new ArrayList<>();
        for (String roleId : roleIds) {
            adminUserRoleList.add(new AdminUserRole().setUserId(userId).setRoleId(Integer.parseInt(roleId)));
        }
        saveBatch(adminUserRoleList, Const.BATCH_SAVE_SIZE);
    }
}
