package com.springclouddev.loges.logback.appender;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.springclouddev.loges.core.MessageAppenderFactory;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.kafka.KafkaProducerClient;
import com.springclouddev.loges.logback.util.LogMessageUtil;


public class KafkaAppender extends AppenderBase<ILoggingEvent> {
    private KafkaProducerClient kafkaClient;
    private String appName;
    private String kafkaHosts;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setKafkaHosts(String kafkaHosts) {
        this.kafkaHosts = kafkaHosts;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (kafkaClient == null) {
            kafkaClient = KafkaProducerClient.getInstance(this.kafkaHosts);
        }
        final BaseLogMessage logMessage = LogMessageUtil.getLogMessage(appName, event);
        MessageAppenderFactory.push(logMessage, kafkaClient);
    }
}
