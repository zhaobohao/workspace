

package com.dc3.common.sdk.service;

import com.dc3.common.sdk.bean.ScheduleProperty;

/**
 * @author pnoker
 */
public interface DriverScheduleService {
    /**
     * 初始化调度任务
     *
     * @param scheduleProperty ScheduleProperty
     */
    void initial(ScheduleProperty scheduleProperty);
}
