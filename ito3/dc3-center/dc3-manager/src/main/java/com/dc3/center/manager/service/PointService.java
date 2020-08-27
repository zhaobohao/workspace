

package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.PointDto;
import com.dc3.common.model.Point;

/**
 * <p>Point Interface
 *
 * @author pnoker
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
