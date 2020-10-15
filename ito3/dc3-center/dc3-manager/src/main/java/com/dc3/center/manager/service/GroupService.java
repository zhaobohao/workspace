package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.GroupDto;
import com.dc3.common.model.Group;

/**
 * <p>Group Interface
 *

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
