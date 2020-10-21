package org.springclouddev.iot.data.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.iot.data.dto.PointValueDto;
import org.springclouddev.iot.data.entity.PointValue;

import java.util.List;


public interface PointValueService {

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
     * @return PointValue Array
     */
    List<PointValue> realtime(Long deviceId);

    /**
     * 获取实时数据
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return PointValue
     */
    PointValue realtime(Long deviceId, Long pointId);

    /**
     * 获取最新的一个位号数据
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return PointValue
     */
    PointValue latest(Long deviceId, Long pointId);

    /**
     * 新增 PointValue
     *
     * @param pointValue PointValue
     */
    void addPointValue(PointValue pointValue);

    /**
     * 批量新增 PointValue
     *
     * @param pointValues PointValue Array
     */
    void addPointValues(List<PointValue> pointValues);

    /**
     * 获取带分页、排序
     *
     * @param pointValueDto PointValueDto
     * @return Page<PointValue>
     */
    Page<PointValue> list(PointValueDto pointValueDto);

}
