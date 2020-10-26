package org.springclouddev.crm.admin.service.impl;

import org.springclouddev.crm.admin.api.entity.AdminSystemLog;
import org.springclouddev.crm.admin.mapper.AdminSystemLogMapper;
import org.springclouddev.crm.admin.service.IAdminSystemLogService;
import org.springclouddev.crm.core.servlet.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
@Service
public class AdminSystemLogServiceImpl extends BaseServiceImpl<AdminSystemLogMapper, AdminSystemLog> implements IAdminSystemLogService {

}
