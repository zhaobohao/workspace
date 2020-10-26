package org.springclouddev.crm.admin.mapper;

import org.springclouddev.crm.admin.api.entity.AdminMenu;
import org.springclouddev.crm.admin.api.entity.AdminRole;
import org.springclouddev.crm.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 查询角色菜单
     *
     * @param parentId parentId
     * @param roleId   roleId
     * @return data
     */
    public List<Integer> getRoleMenu(@Param("parentId") Integer parentId, @Param("roleId") Integer roleId);

    /**
     * 查询数据权限
     *
     * @param userId 用户ID
     * @param menuId 菜单ID
     * @return 权限
     */
    public List<Integer> queryDataType(@Param("userId") Long userId, @Param("menuId") Integer menuId);

    /**
     * 查询角色菜单列表
     * @param userId 用户ID
     * @param realm 权限标识
     * @return data
     */
    public List<AdminMenu> queryUserRoleListByUserId(@Param("userId") Long userId, @Param("realm") String realm);
    /**
     * 查询数据权限
     *
     * @param userId 用户ID
     * @param menuId 菜单ID
     * @return 权限
     */
    List<Integer> queryMaxDataType(@Param("userId") Long userId, @Param("menuId") Integer menuId);

    void deleteWorkRole(@Param("roleId") Integer roleId, @Param("editRoleId") Integer editRoleId);

    List<AdminRole> queryRoleList();
}
