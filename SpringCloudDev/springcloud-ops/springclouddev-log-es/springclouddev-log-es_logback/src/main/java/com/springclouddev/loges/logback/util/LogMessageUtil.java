package com.springclouddev.loges.logback.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.springclouddev.loges.core.LogMessageThreadLocal;
import com.springclouddev.loges.core.TraceMessage;
import com.springclouddev.loges.core.constant.LogMessageConstant;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.dto.RunLogMessage;
import com.springclouddev.loges.core.util.TraceLogMessageFactory;

public class LogMessageUtil {

    public static BaseLogMessage getLogMessage(final String appName, final ILoggingEvent iLoggingEvent) {
        TraceMessage traceMessage = LogMessageThreadLocal.logMessageThreadLocal.get();
        String formattedMessage = iLoggingEvent.getFormattedMessage();
        if (formattedMessage.startsWith(LogMessageConstant.TRACE_PRE)) {
            return TraceLogMessageFactory.getTraceLogMessage(
                    traceMessage, appName, iLoggingEvent.getTimeStamp());
        }
        RunLogMessage logMessage =
                TraceLogMessageFactory.getLogMessage(appName, formattedMessage, iLoggingEvent.getTimeStamp());
        StackTraceElement stackTraceElement = iLoggingEvent.getCallerData()[0];
        logMessage.setClassName(stackTraceElement.getClassName());
        logMessage.setMethod(stackTraceElement.getMethodName());
        logMessage.setLogLevel(iLoggingEvent.getLevel().toString());
        return logMessage;
    }
}
