package org.springclouddev.crm.admin.service;

import org.springclouddev.crm.admin.api.entity.AdminRoleMenu;
import org.springclouddev.crm.core.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * 角色菜单对应关系表 服务类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
public interface IAdminRoleMenuService extends BaseService<AdminRoleMenu> {
    public void saveRoleMenu(Integer roleId, List<Integer> menuIdList);
}
