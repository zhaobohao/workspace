package com.springclouddev.loges.log4j.util;

import com.springclouddev.loges.core.LogMessageThreadLocal;
import com.springclouddev.loges.core.TraceMessage;
import com.springclouddev.loges.core.constant.LogMessageConstant;
import com.springclouddev.loges.core.dto.BaseLogMessage;
import com.springclouddev.loges.core.dto.RunLogMessage;
import com.springclouddev.loges.core.util.LogExceptionStackTrace;
import com.springclouddev.loges.core.util.TraceLogMessageFactory;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

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
        String formattedMessage = getMessage(loggingEvent);
        if (formattedMessage.startsWith(LogMessageConstant.TRACE_PRE)) {
            return TraceLogMessageFactory.getTraceLogMessage(
                    traceMessage, appName, loggingEvent.getTimeStamp());
        }
        RunLogMessage logMessage =
                TraceLogMessageFactory.getLogMessage(appName, formattedMessage, loggingEvent.getTimeStamp());
        logMessage.setClassName(loggingEvent.getLoggerName());
        logMessage.setMethod(loggingEvent.getThreadName());
        logMessage.setLogLevel(loggingEvent.getLevel().toString());
        return logMessage;
    }


    private static String getMessage(LoggingEvent logEvent) {
        if (logEvent.getLevel().toInt() == Priority.ERROR_INT) {
            String msg = "";
            if (logEvent.getThrowableInformation() != null){
                msg = LogExceptionStackTrace.erroStackTrace(
                        logEvent.getThrowableInformation().getThrowable()).toString();
            }
            if (logEvent.getRenderedMessage().indexOf(LogMessageConstant.DELIM_STR) > 0) {
                FormattingTuple format = MessageFormatter.format(logEvent.getRenderedMessage(), msg);
                return format.getMessage();
            }
            return logEvent.getRenderedMessage() + "\n" + msg;
        }
        return logEvent.getRenderedMessage();
    }
}
