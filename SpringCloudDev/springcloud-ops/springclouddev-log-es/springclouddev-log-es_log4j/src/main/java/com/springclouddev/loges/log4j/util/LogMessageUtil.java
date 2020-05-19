package com.springclouddev.loges.log4j.util;

import com.springclouddev.loges.core.LogMessageThreadLocal;
import com.springclouddev.loges.core.TraceMessage;
import com.springclouddev.loges.core.constant.LogMessageConstant;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.dto.RunLogMessage;
import com.springclouddev.loges.core.util.TraceLogMessageFactory;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @Author Frank.chen
 * @Description //TODO
 * @Date 15:56 2020/4/27
 **/
public class LogMessageUtil {

    /**
     * @return com.springclouddev.loges.core.LogMessage
     * @Author Frank.chen
     * @Description //TODO
     * @Date 15:56 2020/4/27
     * @Param [appName, loggingEvent]
     **/
    public static BaseLogMessage getLogMessage(String appName, LoggingEvent loggingEvent) {
        TraceMessage traceMessage = LogMessageThreadLocal.logMessageThreadLocal.get();
        String formattedMessage = loggingEvent.getRenderedMessage();
        if (formattedMessage.startsWith(LogMessageConstant.TRACE_PRE)) {
            return TraceLogMessageFactory.getTraceLogMessage(
                    traceMessage, appName, loggingEvent.getTimeStamp());
        }
        RunLogMessage logMessage =
                TraceLogMessageFactory.getLogMessage(appName, formattedMessage, loggingEvent.getTimeStamp());
        logMessage.setClassName(loggingEvent.getLoggerName());
        logMessage.setMethod(loggingEvent.getLocationInformation().getMethodName());
        logMessage.setLogLevel(loggingEvent.getLevel().toString());
        return logMessage;
    }
}
