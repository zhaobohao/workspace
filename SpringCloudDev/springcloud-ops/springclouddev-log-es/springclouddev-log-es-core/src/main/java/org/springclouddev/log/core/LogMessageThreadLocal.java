package org.springclouddev.log.core;


import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * className：LogMessageThreadLocal
 * description：LogMessageThreadLocal 用来存储trace相关信息
 *
 * @author Frank.chen
 * @version 1.0.0
 */
public class LogMessageThreadLocal {
    public static TransmittableThreadLocal<TraceMessage> logMessageThreadLocal = new TransmittableThreadLocal<>();
}
