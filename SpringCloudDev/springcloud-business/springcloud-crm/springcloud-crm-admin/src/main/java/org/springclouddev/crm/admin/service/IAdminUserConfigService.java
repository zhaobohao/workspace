package org.springclouddev.crm.admin.service;

import org.springclouddev.crm.admin.api.entity.AdminUserConfig;
import org.springclouddev.crm.core.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * 用户配置表 服务类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
public interface IAdminUserConfigService extends BaseService<AdminUserConfig> {

    /**
     * 根据名称查询用户配置信息
     * userId获取当前登录人
     * @param name 名称
     * @return data
     */
    public AdminUserConfig queryUserConfigByName(String name);

    /**
     * 根据名称查询用户配置信息列表
     * userId获取当前登录人
     * @param name 名称
     * @return data
     */
    public List<AdminUserConfig> queryUserConfigListByName(String name);

    /**
     * 根据名称删除用户配置信息
     * userId获取当前登录人
     * @param name 名称
     */
    public void deleteUserConfigByName(String name);


    /**
     * 用户注册的时候初始化用户配置
     * @param userId 新增的用户ID
     */
    public void initUserConfig(Long userId);
}
