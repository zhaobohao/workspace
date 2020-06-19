package org.springclouddev.log.log4j.appender;

import org.springclouddev.log.core.constant.LogMessageConstant;
import org.springclouddev.log.log4j.util.LogMessageUtil;
import org.springclouddev.log.core.MessageAppenderFactory;
import org.springclouddev.log.core.dto.BaseLogMessage;
import org.springclouddev.log.core.kafka.KafkaProducerClient;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * className：KafkaAppender
 * description：KafkaAppender 如果使用kafka作为队列用这个KafkaAppender输出
 *
 * @author Frank.chen
 * @version 1.0.0
 */
public class KafkaAppender extends AppenderSkeleton {
    private KafkaProducerClient kafkaClient;
    private String appName;
    private String kafkaHosts;
    private String runModel;
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

    public void setRunModel(String runModel) {
        this.runModel = runModel;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        if(this.runModel!=null){
            LogMessageConstant.RUN_MODEL=Integer.parseInt(this.runModel);
        }
        if (kafkaClient == null) {
            kafkaClient = KafkaProducerClient.getInstance(kafkaHosts);
        }
        final BaseLogMessage logMessage = LogMessageUtil.getLogMessage(this.appName, loggingEvent);
        MessageAppenderFactory.push(logMessage, kafkaClient,"plume.log.ack");
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
