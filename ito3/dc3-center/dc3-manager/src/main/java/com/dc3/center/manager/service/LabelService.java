package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.LabelDto;
import com.dc3.common.model.Label;

/**
 * <p>Label Interface
 *

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
