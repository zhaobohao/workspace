package com.springclouddev.loges.core.disruptor;

import com.springclouddev.loges.core.AbstractClient;
import com.springclouddev.loges.core.constant.LogMessageConstant;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.dto.RunLogMessage;
import com.springclouddev.loges.core.util.GfJsonUtil;
import com.lmax.disruptor.WorkHandler;

/**
 * className：LogMessageConsumer
 * description： 日志消费
 * time：2020-05-19.13:59
 *
 * @author Tank
 * @version 1.0.0
 */
public class LogMessageConsumer implements WorkHandler<LogEvent> {

    private String name;

    public LogMessageConsumer(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(LogEvent event) throws Exception {
        BaseLogMessage baseLogMessage = event.getBaseLogMessage();
        final String redisKey =
                baseLogMessage instanceof RunLogMessage
                        ? LogMessageConstant.LOG_KEY
                        : LogMessageConstant.LOG_KEY_TRACE;
        AbstractClient.getClient().pushMessage(redisKey, GfJsonUtil.toJSONString(baseLogMessage));
    }
}
