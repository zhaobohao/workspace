package org.springclouddev.crm.admin.service;

import org.springclouddev.crm.admin.api.bo.AdminMessageQueryBO;
import org.springclouddev.crm.admin.api.entity.AdminMessage;
import org.springclouddev.crm.admin.api.entity.AdminMessageBO;
import org.springclouddev.crm.admin.api.vo.AdminMessageVO;
import org.springclouddev.crm.core.entity.BasePage;
import org.springclouddev.crm.core.servlet.BaseService;
import org.springclouddev.crm.core.servlet.BaseService;

/**
 * <p>
 * 系统消息表 服务类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
public interface IAdminMessageService extends BaseService<AdminMessage> {

    /**
     * 新增或修改消息
     *
     * @param message message
     */
    public Long saveOrUpdateMessage(AdminMessage message);

    /**
     * 查询消息列表
     * @param adminMessageBO 搜索对象
     * @return data
     */
    public BasePage<AdminMessage> queryList(AdminMessageQueryBO adminMessageBO);

    /**
     * 查询未读消息数量
     * @return data
     */
    public AdminMessageVO queryUnreadCount();

    /**
     * 新增消息
     *
     * @param adminMessageBO message
     */
    public void addMessage(AdminMessageBO adminMessageBO);

    void deleteEventMessage(Integer eventId);

    void deleteById(Integer messageId);
}
