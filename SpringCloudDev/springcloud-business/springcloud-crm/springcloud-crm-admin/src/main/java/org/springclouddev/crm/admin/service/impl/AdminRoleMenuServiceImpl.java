package org.springclouddev.crm.admin.service.impl;

import org.springclouddev.crm.admin.api.entity.AdminRoleMenu;
import org.springclouddev.crm.admin.mapper.AdminRoleMenuMapper;
import org.springclouddev.crm.admin.service.IAdminRoleMenuService;
import org.springclouddev.crm.core.common.Const;
import org.springclouddev.crm.core.servlet.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色菜单对应关系表 服务实现类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
@Service
public class AdminRoleMenuServiceImpl extends BaseServiceImpl<AdminRoleMenuMapper, AdminRoleMenu> implements IAdminRoleMenuService {

    @Override
    public void saveRoleMenu(Integer roleId, List<Integer> menuIdList) {
        List<AdminRoleMenu> adminRoleMenuList = new ArrayList<>();
        menuIdList.forEach(menuId -> {
            AdminRoleMenu adminRoleMenu = new AdminRoleMenu();
            adminRoleMenu.setMenuId(menuId);
            adminRoleMenu.setRoleId(roleId);
            adminRoleMenuList.add(adminRoleMenu);
        });
        saveBatch(adminRoleMenuList, Const.BATCH_SAVE_SIZE);
    }
}
