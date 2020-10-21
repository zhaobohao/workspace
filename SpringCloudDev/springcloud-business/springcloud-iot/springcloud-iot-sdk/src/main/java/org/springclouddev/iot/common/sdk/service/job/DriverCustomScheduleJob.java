package org.springclouddev.iot.common.sdk.service.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springclouddev.iot.common.sdk.service.CustomDriverService;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自定义调度任务
 *
 */
@Slf4j
@Component
public class DriverCustomScheduleJob extends QuartzJobBean {
    @Resource
    private CustomDriverService customDriverService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("Execute custom schedule");
        customDriverService.schedule();
    }
}