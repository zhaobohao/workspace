package com.dc3.common.sdk.service.job;

import com.dc3.common.sdk.bean.AttributeInfo;
import com.dc3.common.sdk.bean.DriverContext;
import com.dc3.common.sdk.service.DriverCommandService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Read Schedule Job
 *

 */
@Slf4j
@Component
public class DriverReadScheduleJob extends QuartzJobBean {

    @Resource
    private DriverContext driverContext;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private DriverCommandService driverCommandService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Map<Long, Map<Long, Map<String, AttributeInfo>>> pointInfoMap = driverContext.getDevicePointInfoMap();
        pointInfoMap.forEach((deviceId, pointMap) -> pointMap.forEach((pointId, point) -> {
            threadPoolExecutor.execute(() -> {
                log.debug("Execute read schedule for device({}),point({}),{}", deviceId, pointId, point);
                driverCommandService.read(deviceId, pointId);
            });
        }));
    }
}