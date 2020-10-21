package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.LabelDto;
import org.springclouddev.iot.manager.entity.Label;

/**
 * <p>Label Interface
 */
public interface LabelService extends Service<Label, LabelDto> {
    /**
     * 根据标签 NAME 查询
     *
     * @param name
     * @return
     */
    Label selectByName(String name);
}
