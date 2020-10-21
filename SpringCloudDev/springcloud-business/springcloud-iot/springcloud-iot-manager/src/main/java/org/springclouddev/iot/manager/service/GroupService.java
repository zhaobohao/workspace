package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.GroupDto;
import org.springclouddev.iot.manager.entity.Group;

/**
 * <p>Group Interface
 */
public interface GroupService extends Service<Group, GroupDto> {
    /**
     * 根据分组 NAME 查询
     *
     * @param name
     * @return
     */
    Group selectByName(String name);

}
