package com.springclouddev.loges.log4j2.util;

import com.springclouddev.loges.core.LogMessageThreadLocal;
import com.springclouddev.loges.core.TraceMessage;
import com.springclouddev.loges.core.constant.LogMessageConstant;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.dto.RunLogMessage;
import com.springclouddev.loges.core.util.TraceLogMessageFactory;
import org.apache.logging.log4j.core.LogEvent;

/**
 * 组装日志数据
 */
public class LogMessageUtil {

    public static BaseLogMessage getLogMessage(String appName, LogEvent logEvent) {
        TraceMessage traceMessage = LogMessageThreadLocal.logMessageThreadLocal.get();
        String formattedMessage = logEvent.getMessage().getFormattedMessage();
        if (formattedMessage.startsWith(LogMessageConstant.TRACE_PRE)) {
            return TraceLogMessageFactory.getTraceLogMessage(
                    traceMessage, appName, logEvent.getTimeMillis());
        }
        RunLogMessage logMessage =
                TraceLogMessageFactory.getLogMessage(appName, formattedMessage, logEvent.getTimeMillis());
        logMessage.setClassName(logEvent.getSource().getClassName());
        logMessage.setMethod(logEvent.getSource().getMethodName());
        logMessage.setLogLevel(logEvent.getLevel().toString());
        return logMessage;
    }
}
