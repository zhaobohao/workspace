package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.ProfileDto;
import org.springclouddev.iot.manager.entity.Profile;

/**
 * <p>Profile Interface
 */
public interface ProfileService extends Service<Profile, ProfileDto> {
    /**
     * 根据模板 NAME 查询
     *
     * @param name
     * @return
     */
    Profile selectByName(String name);
}
