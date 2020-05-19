package com.springclouddev.loges.log4j2.appender;

import com.springclouddev.loges.core.MessageAppenderFactory;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.redis.RedisClient;
import com.springclouddev.loges.log4j2.util.LogMessageUtil;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;

@Plugin(name = "RedisAppender", category = "Core", elementType = "appender", printObject = true)
public class RedisAppender extends AbstractAppender {
    private RedisClient redisClient;
    private String appName;
    private String reidsHost;
    private String redisPort;

    protected RedisAppender(String name, String appName, String reidsHost, String redisPort, Filter filter, Layout<? extends Serializable> layout,
                            final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
        this.appName = appName;
        this.reidsHost = reidsHost;
        this.redisPort = redisPort;
    }

    @Override
    public void append(LogEvent logEvent) {
        if (redisClient == null) {
            redisClient = RedisClient.getInstance(this.reidsHost, Integer.parseInt(this.redisPort), "");
        }
        final BaseLogMessage logMessage = LogMessageUtil.getLogMessage(this.appName, logEvent);
        MessageAppenderFactory.push(appName, logMessage, redisClient);
    }

    @PluginFactory
    public static RedisAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginAttribute("appName") String appName,
            @PluginAttribute("reidsHost") String reidsHost,
            @PluginAttribute("redisPort") String redisPort,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter) {
        return new RedisAppender(name, appName, reidsHost, redisPort, filter, layout, true);
    }
}
