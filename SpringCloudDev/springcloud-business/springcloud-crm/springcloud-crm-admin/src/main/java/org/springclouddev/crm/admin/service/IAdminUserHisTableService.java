package org.springclouddev.crm.admin.service;

import org.springclouddev.crm.admin.api.entity.AdminUserHisTable;
import org.springclouddev.crm.core.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * 授权坐席 服务类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
public interface IAdminUserHisTableService extends BaseService<AdminUserHisTable> {

    /**
     * 员工坐席授权
     */
    boolean authorize(List<Long> userIds, Integer status);

    /**
     * 判断用户是否为坐席
     * @return
     */
    boolean checkAuth();
}
