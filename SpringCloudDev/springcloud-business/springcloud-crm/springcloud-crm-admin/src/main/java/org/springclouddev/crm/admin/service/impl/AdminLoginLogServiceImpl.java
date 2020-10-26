package org.springclouddev.crm.admin.service.impl;

import org.springclouddev.crm.admin.api.entity.AdminLoginLog;
import org.springclouddev.crm.admin.mapper.AdminLoginLogMapper;
import org.springclouddev.crm.admin.service.IAdminLoginLogService;
import org.springclouddev.crm.core.servlet.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统登录日志表 服务实现类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
@Service
public class AdminLoginLogServiceImpl extends BaseServiceImpl<AdminLoginLogMapper, AdminLoginLog> implements IAdminLoginLogService {

}
