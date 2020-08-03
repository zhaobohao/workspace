package com.dc3.common.sdk.service;

import com.dc3.common.model.Device;
import com.dc3.common.model.Point;
import com.dc3.common.sdk.bean.AttributeInfo;

import java.util.Map;

/**
 * 驱动接口，用于驱动接口实现
 *
 * @author pnoker
 */
public interface CustomDriverService {
    /**
     * Initial Driver
     */
    void initial();

    /**
     * Read Operation
     *
     * @param driverInfo Driver Attribute Info
     * @param pointInfo  Point Attribute Info
     * @param device     Device
     * @param point      Point
     * @return String Value
     * @throws Exception Exception
     */
    String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception;

    /**
     * Write Operation
     *
     * @param driverInfo Driver Attribute Info
     * @param pointInfo  Point Attribute Info
     * @param device     Device
     * @param value      Value Attribute Info
     * @return Boolean Boolean
     * @throws Exception Exception
     */
    Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception;

    /**
     * 驱动本身存在定时器，用于定时采集数据和下发数据，该方法为用户自定义操作，系统根据配置定时执行
     */
    void schedule();
}
