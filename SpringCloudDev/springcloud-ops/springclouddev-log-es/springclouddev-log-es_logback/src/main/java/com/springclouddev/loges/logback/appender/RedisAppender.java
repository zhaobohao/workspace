package com.springclouddev.loges.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.springclouddev.loges.core.MessageAppenderFactory;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.redis.RedisClient;
import com.springclouddev.loges.logback.util.LogMessageUtil;

/**
 * className：RedisAppender
 * description：
 * time：2020-05-19.15:26
 *
 * @author Tank
 * @version 1.0.0
 */
public class RedisAppender extends AppenderBase<ILoggingEvent> {
    private RedisClient redisClient;
    private String appName;
    private String reidsHost;
    private String redisPort;
    private String redisAuth;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setReidsHost(String reidsHost) {
        this.reidsHost = reidsHost;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public void setRedisAuth(String redisAuth) {
        this.redisAuth = redisAuth;
    }

    @Override
    protected void append(ILoggingEvent event) {
        BaseLogMessage logMessage = LogMessageUtil.getLogMessage(appName, event);
        MessageAppenderFactory.push(logMessage,redisClient);
    }

    @Override
    public void start() {
        super.start();
        redisClient = RedisClient.getInstance(this.reidsHost, Integer.parseInt(this.redisPort), this.redisAuth);
    }
}
