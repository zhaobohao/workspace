package org.springclouddev.iot.common.sdk.service;


import org.springclouddev.iot.common.sdk.bean.ScheduleProperty;

public interface DriverScheduleService {
    /**
     * 初始化调度任务
     *
     * @param scheduleProperty ScheduleProperty
     */
    void initial(ScheduleProperty scheduleProperty);
}
