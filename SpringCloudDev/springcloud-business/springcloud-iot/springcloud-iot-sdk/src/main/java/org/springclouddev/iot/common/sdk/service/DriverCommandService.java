package org.springclouddev.iot.common.sdk.service;

import org.springclouddev.iot.data.entityintValue;


public interface DriverCommandService {

    /**
     * 读操作
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return PointValue
     */
    PointValue read(Long deviceId, Long pointId);

    /**
     * 写操作
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @param value    String Value
     * @return Boolean
     */
    Boolean write(Long deviceId, Long pointId, String value);

}
