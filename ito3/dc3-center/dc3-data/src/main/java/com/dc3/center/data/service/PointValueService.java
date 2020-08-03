package com.dc3.center.data.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.common.bean.driver.PointValue;
import com.dc3.common.bean.driver.PointValueDto;

import java.util.List;

/**
 * @author pnoker
 */
public interface PointValueService {

    /**
     * 新增 PointValue
     *
     * @param pointValue
     */
    void add(PointValue pointValue);

    /**
     * 批量新增 PointValue
     *
     * @param pointValues PointValue Array
     */
    void add(List<PointValue> pointValues);

    /**
     * 获取带分页、排序
     *
     * @param pointValueDto PointValueDto
     * @return Page<PointValue>
     */
    Page<PointValue> list(PointValueDto pointValueDto);

    /**
     * 获取最新的一个位号数据
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return PointValue
     */
    PointValue latest(Long deviceId, Long pointId);

    /**
     * 获取设备状态
     *
     * @param deviceId Device Id
     * @return ONLINE, OFFLINE, MAINTAIN, FAULT
     */
    String status(Long deviceId);

    /**
     * 获取实时数据
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return String Value
     */
    String realtime(Long deviceId, Long pointId);
}
