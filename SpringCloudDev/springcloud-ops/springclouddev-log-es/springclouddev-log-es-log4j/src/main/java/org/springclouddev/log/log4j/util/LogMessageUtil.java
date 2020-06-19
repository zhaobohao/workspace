package org.springclouddev.log.log4j.util;

import org.springclouddev.log.core.LogMessageThreadLocal;
import org.springclouddev.log.core.TraceMessage;
import org.springclouddev.log.core.constant.LogMessageConstant;
import org.springclouddev.log.core.dto.BaseLogMessage;
import org.springclouddev.log.core.dto.RunLogMessage;
import org.springclouddev.log.core.util.DateUtil;
import org.springclouddev.log.core.util.LogExceptionStackTrace;
import org.springclouddev.log.core.util.TraceLogMessageFactory;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.Date;

/**
 * className：LogMessageUtil
 * description：组装日志对象
 *
 * @author Frank.chen
 * @version 1.0.0
 */
public class LogMessageUtil {

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
        if(LogMessageConstant.RUN_MODEL==1) {
            logMessage.setMethod(loggingEvent.getThreadName());
        }else {
            LocationInfo locationInfo=loggingEvent.getLocationInformation();
            String method=locationInfo.getMethodName();
            String line=locationInfo.getLineNumber();
            logMessage.setMethod(method+"("+locationInfo.getFileName()+":"+line+")");
            logMessage.setDateTime(DateUtil.parseDateToStr(new Date(loggingEvent.getTimeStamp()),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI));
        }
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
            if (logEvent.getRenderedMessage()!=null&&logEvent.getRenderedMessage().indexOf(LogMessageConstant.DELIM_STR) > 0) {
                FormattingTuple format = MessageFormatter.format(logEvent.getRenderedMessage(), msg);
                return format.getMessage();
            }
            return logEvent.getRenderedMessage() + "\n" + msg;
        }
        return logEvent.getRenderedMessage();
    }
}
