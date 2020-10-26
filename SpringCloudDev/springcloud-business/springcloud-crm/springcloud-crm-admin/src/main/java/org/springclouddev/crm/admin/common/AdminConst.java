package org.springclouddev.crm.admin.common;

import org.springclouddev.crm.core.common.Const;

/**
 * @author zhangzhiwei
 * 系统管理模块的常量
 */
public class AdminConst extends Const {
    /**
     * 默认的权限缓存KEY
     */
    public static final String DEFAULT_AUTH_CACHE_NAME = "CRM:AUTH";

    /**
     * 默认的密码强度正则
     */
    public static final String DEFAULT_PASSWORD_INTENSITY = "^(?=.*[a-zA-Z])(?=.*\\d).{6,20}$";

}
