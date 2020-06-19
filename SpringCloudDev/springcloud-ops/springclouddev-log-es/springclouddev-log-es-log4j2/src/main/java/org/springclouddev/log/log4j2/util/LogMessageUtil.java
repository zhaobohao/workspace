package org.springclouddev.log.log4j2.util;

import org.springclouddev.log.core.LogMessageThreadLocal;
import org.springclouddev.log.core.TraceId;
import org.springclouddev.log.core.TraceMessage;
import org.springclouddev.log.core.constant.LogMessageConstant;
import org.springclouddev.log.core.dto.BaseLogMessage;
import org.springclouddev.log.core.dto.RunLogMessage;
import org.springclouddev.log.core.util.DateUtil;
import org.springclouddev.log.core.util.LogExceptionStackTrace;
import org.springclouddev.log.core.util.TraceLogMessageFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;

import java.util.Date;

import static org.apache.logging.log4j.message.ParameterizedMessageFactory.INSTANCE;


/**
 * className：TraceAspect
 * description：
 * time：2020-05-19.14:34
 *
 * @author Tank
 * @version 1.0.0
 */
public class LogMessageUtil {

    private static String isExpandRunLog(LogEvent logEvent) {
        String traceId = null;
        if (LogMessageConstant.EXPAND.equals(LogMessageConstant.SLEUTH_EXPAND)) {
            if (!logEvent.getContextData().isEmpty()) {
                traceId = logEvent.getContextData().toMap().get(LogMessageConstant.TRACE_ID);
                TraceId.logTraceID.set(traceId);
            }
        }
        return traceId;
    }


    public static BaseLogMessage getLogMessage(String appName, LogEvent logEvent) {
        String traceId = isExpandRunLog(logEvent);
        TraceMessage traceMessage = LogMessageThreadLocal.logMessageThreadLocal.get();
        String formattedMessage = getMessage(logEvent);
        if (formattedMessage.startsWith(LogMessageConstant.TRACE_PRE)) {
            if (!LogMessageConstant.EXPAND.equals(LogMessageConstant.DEFAULT_EXPAND) && traceId != null) {
                traceMessage.setTraceId(traceId);
            }
            return TraceLogMessageFactory.getTraceLogMessage(
                    traceMessage, appName, logEvent.getTimeMillis());
        }
        RunLogMessage logMessage =
                TraceLogMessageFactory.getLogMessage(appName, formattedMessage, logEvent.getTimeMillis());
        logMessage.setClassName(logEvent.getLoggerName());
        if (LogMessageConstant.RUN_MODEL == 1) {
            logMessage.setMethod(logEvent.getThreadName());
        } else {
            StackTraceElement atackTraceElement = logEvent.getSource();
            String method = atackTraceElement.getMethodName();
            String line = String.valueOf(atackTraceElement.getLineNumber());
            logMessage.setMethod(method + "(" +atackTraceElement.getFileName()+":"+ line + ")");
            logMessage.setDateTime(DateUtil.parseDateToStr(new Date(logEvent.getTimeMillis()), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI));
        }
        logMessage.setLogLevel(logEvent.getLevel().toString());
        return logMessage;
    }

    private static String getMessage(LogEvent logEvent) {
        if (logEvent.getLevel().equals(Level.ERROR)) {
            Object[] msg = new String[1];
            if (logEvent.getThrown() != null) {
                msg[0] = LogExceptionStackTrace.erroStackTrace(logEvent.getThrown()).toString();
                return packageMessage(logEvent.getMessage().getFormat(), msg);
            } else {
                if (logEvent.getMessage().getParameters() != null) {
                    Object[] args = logEvent.getMessage().getParameters();
                    if (args != null) {
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Throwable) {
                                args[i] = LogExceptionStackTrace.erroStackTrace(args[i]);
                            }
                        }
                        return packageMessage(logEvent.getMessage().getFormat(), args);
                    }
                }
            }
        }
        return logEvent.getMessage().getFormattedMessage();
    }

    private static String packageMessage(String message, Object[] args) {
        if (message!=null&&message.indexOf(LogMessageConstant.DELIM_STR) > 0) {
            return INSTANCE.newMessage(message, args).getFormattedMessage();
        }
        return TraceLogMessageFactory.packageMessage(message, args);
    }
}
