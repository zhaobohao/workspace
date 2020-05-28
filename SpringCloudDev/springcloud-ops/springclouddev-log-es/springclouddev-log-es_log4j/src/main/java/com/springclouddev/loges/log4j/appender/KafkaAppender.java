package com.springclouddev.loges.log4j.appender;

import com.springclouddev.loges.core.MessageAppenderFactory;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.kafka.KafkaProducerClient;
import com.springclouddev.loges.log4j.util.LogMessageUtil;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;


public class KafkaAppender extends AppenderSkeleton {
    private KafkaProducerClient kafkaClient;
    private String appName;
    private String kafkaHosts;
    private String topic;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setKafkaHosts(String kafkaHosts) {
        this.kafkaHosts = kafkaHosts;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        if (kafkaClient == null) {
            kafkaClient = KafkaProducerClient.getInstance(kafkaHosts);
        }
        final BaseLogMessage logMessage = LogMessageUtil.getLogMessage(this.appName, loggingEvent);
        MessageAppenderFactory.push(logMessage, kafkaClient);
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
