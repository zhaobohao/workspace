package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.PointDto;
import org.springclouddev.iot.manager.entityint;

/**
 * <p>Point Interface
 */
public interface PointService extends Service<Point, PointDto> {

    /**
     * 根据位号 NAME & 模板 ID 查询
     *
     * @param name      Point Name
     * @param profileId Profile Id
     * @return
     */
    Point selectByNameAndProfile(String name, Long profileId);
}
