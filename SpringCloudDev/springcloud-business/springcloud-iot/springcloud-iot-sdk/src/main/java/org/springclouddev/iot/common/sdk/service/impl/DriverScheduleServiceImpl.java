package org.springclouddev.iot.common.sdk.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springclouddev.iot.common.sdk.bean.ScheduleProperty;
import org.springclouddev.iot.common.sdk.service.DriverScheduleService;
import org.springclouddev.iot.common.sdk.service.job.DriverCustomScheduleJob;
import org.springclouddev.iot.common.sdk.service.job.DriverReadScheduleJob;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;


@Slf4j
@Service
public class DriverScheduleServiceImpl implements DriverScheduleService {
    @Resource
    private Scheduler scheduler;

    @Override
    public void initial(ScheduleProperty scheduleProperty) {
        Optional.ofNullable(scheduleProperty).ifPresent(property -> {
            if (property.getRead().getEnable()) {
                createJob("ReadGroup", "ReadScheduleJob", property.getRead().getCorn(), DriverReadScheduleJob.class);
            }
            if (property.getCustom().getEnable()) {
                createJob("CustomGroup", "CustomScheduleJob", property.getCustom().getCorn(), DriverCustomScheduleJob.class);
            }
            if (property.getRead().getEnable() || property.getCustom().getEnable()) {
                try {
                    if (!scheduler.isShutdown()) {
                        scheduler.start();
                    }
                } catch (SchedulerException e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    /**
     * 创建调度任务
     *
     * @param group    group
     * @param name     name
     * @param corn     corn
     * @param jobClass class
     */
    @SneakyThrows
    public void createJob(String group, String name, String corn, Class<? extends Job> jobClass) {
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(name, group).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(name, group)
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(CronScheduleBuilder.cronSchedule(corn))
                .startNow().build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
