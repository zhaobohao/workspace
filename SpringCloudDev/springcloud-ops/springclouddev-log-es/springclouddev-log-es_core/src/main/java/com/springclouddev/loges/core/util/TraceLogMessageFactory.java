package com.springclouddev.loges.core.util;

import com.springclouddev.loges.core.TraceId;
import com.springclouddev.loges.core.TraceMessage;
import com.springclouddev.loges.core.dto.RunLogMessage;
import com.springclouddev.loges.core.dto.TraceLogMessage;

import java.sql.Timestamp;

/**
 * className：TraceLogMessageFactory
 * description： TODO
 * time：2020-05-13.14:04
 *
 * @author Tank
 * @version 1.0.0
 */
public class TraceLogMessageFactory<T> {

    public static TraceLogMessage getTraceLogMessage(TraceMessage traceMessage, String appName, long time) {
        TraceLogMessage traceLogMessage = new TraceLogMessage();
        traceLogMessage.setAppName(appName);
        traceLogMessage.setTraceId(traceMessage.getTraceId());
        traceLogMessage.setMethod(traceMessage.getMessageType());
        traceLogMessage.setTime(time);
        traceLogMessage.setPosition(traceMessage.getPosition());
        traceLogMessage.setPositionNum(traceMessage.getPositionNum().get());
        return traceLogMessage;
    }

    public static RunLogMessage getLogMessage(String appName, String message, long time) {
        RunLogMessage logMessage = new RunLogMessage();
        String ip = IpGetter.getIp();
        logMessage.setServerName(ip);
        logMessage.setAppName(appName);
        logMessage.setContent(message);
        logMessage.setDateTime(DateUtil.parseTimestampToStr(
                new Timestamp(time),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        logMessage.setDtTime(time);
        logMessage.setTraceId(TraceId.logTraceID.get());
        return logMessage;
    }
}
