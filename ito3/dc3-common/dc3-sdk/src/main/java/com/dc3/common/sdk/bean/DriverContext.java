

package com.dc3.common.sdk.bean;

import com.dc3.common.exception.ServiceException;
import com.dc3.common.model.Device;
import com.dc3.common.model.Point;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pnoker
 */
@Slf4j
@Getter
@Component
public class DriverContext {

    private volatile long driverId;

    /**
     * profileId(driverAttribute.name,(drverInfo.value,driverAttribute.type))
     */
    private volatile Map<Long, Map<String, AttributeInfo>> driverInfoMap = new HashMap<>(16);

    /**
     * deviceId,device
     */
    private volatile Map<Long, Device> deviceMap = new HashMap<>(16);

    /**
     * deviceName,deviceId
     */
    private volatile Map<String, Long> deviceNameMap = new HashMap<>(16);

    /**
     * profileId,(pointId,point)
     */
    private volatile Map<Long, Map<Long, Point>> profilePointMap = new HashMap<>(16);

    /**
     * deviceId(pointId(pointAttribute.name,(pointInfo.value,pointAttribute.type)))
     */
    private volatile Map<Long, Map<Long, Map<String, AttributeInfo>>> devicePointInfoMap = new HashMap<>(16);

    /**
     * deviceId(pointName,pointId)
     */
    private volatile Map<Long, Map<String, Long>> devicePointNameMap = new HashMap<>(16);

    /**
     * 获取设备
     *
     * @param deviceId Device ID
     * @return Device
     */
    public Device getDevice(Long deviceId) {
        Device device = deviceMap.get(deviceId);
        if (null == device) {
            throw new ServiceException("Device(" + deviceId + ") does not exist");
        }
        return device;
    }

    /**
     * 通过 Device Name 获取设备 ID
     *
     * @param deviceName Device Name
     * @return Device ID
     */
    public Long getDeviceIdByName(String deviceName) {
        Long deviceId = deviceNameMap.get(deviceName);
        if (null == deviceId) {
            throw new ServiceException("Device(" + deviceName + ") does not exist");
        }
        return deviceId;
    }

    /**
     * 获取设备位号
     *
     * @param deviceId Device ID
     * @param pointId  Point ID
     * @return Point
     */
    public Point getDevicePoint(Long deviceId, Long pointId) {
        Map<Long, Point> map = profilePointMap.get(getDevice(deviceId).getProfileId());
        if (null == map) {
            throw new ServiceException("Device(" + deviceId + ") profile does not exist");
        }
        Point point = map.get(pointId);
        if (null == point) {
            throw new ServiceException("Point(" + pointId + ") point does not exist");
        }
        return point;
    }

    /**
     * 通过 Device ID & Point Name 获取位号 ID
     *
     * @param deviceId  Device ID
     * @param pointName Point Name
     * @return Device Point ID
     */
    public Long getDevicePointIdByName(Long deviceId, String pointName) {
        Map<String, Long> map = devicePointNameMap.get(deviceId);
        if (null == map) {
            throw new ServiceException("Device(" + deviceId + ") does not exist");
        }
        Long pointId = map.get(pointName);
        if (null == pointId) {
            throw new ServiceException("Point(" + pointName + ") does not exist");
        }
        return pointId;
    }

    /**
     * 获取 驱动信息
     *
     * @param profileId Profile ID
     * @return Map<String, AttributeInfo>
     */
    public Map<String, AttributeInfo> getProfileDriverInfo(Long profileId) {
        return driverInfoMap.get(profileId);
    }

    /**
     * 通过 Device Id & Point Id 获取设备位号配置信息
     *
     * @param deviceId Device ID
     * @param pointId  Point ID
     * @return Map<String, AttributeInfo>
     */
    public Map<String, AttributeInfo> getDevicePointInfo(Long deviceId, Long pointId) {
        Map<Long, Map<String, AttributeInfo>> tmpMap = devicePointInfoMap.get(deviceId);
        if (null == tmpMap) {
            throw new ServiceException("Device(" + deviceId + ") does not exist");
        }
        Map<String, AttributeInfo> infoMap = tmpMap.get(pointId);
        if (null == infoMap) {
            throw new ServiceException("Point(" + pointId + ") info does not exist");
        }
        return infoMap;
    }

    public synchronized void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public synchronized void setDriverInfoMap(Map<Long, Map<String, AttributeInfo>> driverInfoMap) {
        this.driverInfoMap = driverInfoMap;
    }

    public synchronized void setDeviceMap(Map<Long, Device> deviceMap) {
        this.deviceMap = deviceMap;
    }

    public synchronized void setDeviceNameMap(Map<String, Long> deviceNameMap) {
        this.deviceNameMap = deviceNameMap;
    }

    public synchronized void setProfilePointMap(Map<Long, Map<Long, Point>> profilePointMap) {
        this.profilePointMap = profilePointMap;
    }

    public synchronized void setDevicePointInfoMap(Map<Long, Map<Long, Map<String, AttributeInfo>>> devicePointInfoMap) {
        this.devicePointInfoMap = devicePointInfoMap;
    }

    public synchronized void setDevicePointNameMap(Map<Long, Map<String, Long>> devicePointNameMap) {
        this.devicePointNameMap = devicePointNameMap;
    }
}
