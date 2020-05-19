package com.springclouddev.loges.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.springclouddev.loges.core.MessageAppenderFactory;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.redis.RedisClient;
import com.springclouddev.loges.logback.util.LogMessageUtil;


public class RedisAppender extends AppenderBase<ILoggingEvent> {
    private RedisClient redisClient;
    private String appName;
    private String reidsHost;
    private String redisPort;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setReidsHost(String reidsHost) {
        this.reidsHost = reidsHost;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }


    @Override
    protected void append(ILoggingEvent event) {
        if (redisClient == null) {
            redisClient = RedisClient.getInstance(this.reidsHost, Integer.parseInt(this.redisPort), "");
        }
        BaseLogMessage logMessage = LogMessageUtil.getLogMessage(appName, event);
        MessageAppenderFactory.push(appName, logMessage, redisClient);
    }
}
