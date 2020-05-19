package com.springclouddev.loges.log4j2.appender;

import com.springclouddev.loges.core.LogMessage;
import com.springclouddev.loges.core.MessageAppenderFactory;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.kafka.KafkaProducerClient;
import com.springclouddev.loges.core.util.GfJsonUtil;
import com.springclouddev.loges.core.util.ThreadPoolUtil;
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
import java.util.concurrent.ThreadPoolExecutor;

@Plugin(name = "KafkaAppender", category = "Core", elementType = "appender", printObject = true)
public class KafkaAppender extends AbstractAppender {
    private ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getPool();
    private KafkaProducerClient kafkaClient;
    private String appName;
    private String kafkaHosts;
    private String topic;

    protected KafkaAppender(String name, String appName,String kafkaHosts,String topic, Filter filter, Layout<? extends Serializable> layout,
                             final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
        this.appName = appName;
        this.kafkaHosts = kafkaHosts;
        this.topic = topic;
    }
    @Override
    public void append(LogEvent logEvent) {
        if(kafkaClient==null){
            kafkaClient= KafkaProducerClient.getInstance(kafkaHosts);
        }
        final BaseLogMessage logMessage = LogMessageUtil.getLogMessage(this.appName,logEvent);
        MessageAppenderFactory.push(appName, logMessage, kafkaClient);

    }
    @PluginFactory
    public static KafkaAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginAttribute("appName") String appName,
            @PluginAttribute("kafkaHosts") String kafkaHosts,
            @PluginAttribute("topic") String topic,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter) {

        return new KafkaAppender(name, appName,kafkaHosts, topic,filter, layout, true);
    }
}
