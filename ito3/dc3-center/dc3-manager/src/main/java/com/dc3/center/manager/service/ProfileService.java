package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.ProfileDto;
import com.dc3.common.model.Profile;

/**
 * <p>Profile Interface
 *
 * @author pnoker
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
