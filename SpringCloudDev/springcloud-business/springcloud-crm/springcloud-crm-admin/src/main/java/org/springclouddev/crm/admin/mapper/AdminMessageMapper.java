package org.springclouddev.crm.admin.mapper;

import org.springclouddev.crm.admin.api.bo.AdminMessageQueryBO;
import org.springclouddev.crm.admin.api.entity.AdminMessage;
import org.springclouddev.crm.admin.api.vo.AdminMessageVO;
import org.springclouddev.crm.core.entity.BasePage;
import org.springclouddev.crm.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统消息表 Mapper 接口
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
public interface AdminMessageMapper extends BaseMapper<AdminMessage> {
    public BasePage<AdminMessage> queryList(BasePage<AdminMessage> parse, @Param("data") AdminMessageQueryBO adminMessageBO);

    public AdminMessageVO queryUnreadCount(@Param("userId") Long userId);
}
