package org.springclouddev.iot.manager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.iot.common.bean.DriverOperation;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.manager.entity.Driver;
import org.springclouddev.iot.manager.service.DriverService;
import org.springclouddev.iot.manager.service.NotifyService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * NotifyService Impl
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    @Resource
    private DriverService driverService;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void notifyDriverProfile(Driver driver, Long profileId, String operationType) {
        if (null != driver) {
            DriverOperation operation = new DriverOperation().setCommand(operationType).setId(profileId);
            notifyDriver(driver, operation);
        }
    }

    @Override
    public void notifyDriverDevice(Long deviceId, Long profileId, String operationType) {
        Driver driver = driverService.selectByProfileId(profileId);
        if (null != driver) {
            DriverOperation operation = new DriverOperation().setCommand(operationType).setId(deviceId);
            notifyDriver(driver, operation);
        }
    }

    @Override
    public void notifyDriverPoint(Long pointId, Long profileId, String operationType) {
        Driver driver = driverService.selectByProfileId(profileId);
        if (null != driver) {
            DriverOperation operation = new DriverOperation().setCommand(operationType).setId(pointId).setParentId(profileId);
            notifyDriver(driver, operation);
        }
    }


    @Override
    public void notifyDriverDriverInfo(Long driverInfoId, Long attributeId, Long profileId, String operationType) {
        Driver driver = driverService.selectByProfileId(profileId);
        if (null != driver) {
            DriverOperation operation = new DriverOperation().setCommand(operationType).setId(driverInfoId).setParentId(profileId).setAttributeId(attributeId);
            notifyDriver(driver, operation);
        }
    }

    @Override
    public void notifyDriverPointInfo(Long pointInfoId, Long attributeId, Long deviceId, String operationType) {
        Driver driver = driverService.selectByDeviceId(deviceId);
        if (null != driver) {
            DriverOperation operation = new DriverOperation().setCommand(operationType).setId(pointInfoId).setParentId(deviceId).setAttributeId(attributeId);
            notifyDriver(driver, operation);
        }
    }

    /**
     * notify driver
     *
     * @param driver    Driver
     * @param operation DriverOperation
     */
    private void notifyDriver(Driver driver, DriverOperation operation) {
        log.debug("Notify Driver {} : {}", driver.getServiceName(), operation);
        rabbitTemplate.convertAndSend(Common.Rabbit.TOPIC_EXCHANGE_NOTIFY, Common.Rabbit.ROUTING_DEVICE_NOTIFY_PREFIX + driver.getServiceName(), operation);
    }

}
