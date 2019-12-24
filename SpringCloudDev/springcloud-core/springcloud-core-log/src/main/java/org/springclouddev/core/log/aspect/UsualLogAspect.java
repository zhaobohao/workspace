package org.springclouddev.core.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springclouddev.core.log.annotation.UsualLog;
import org.springclouddev.core.log.logger.SystemLogger;
import org.springclouddev.core.log.publisher.UsualLogPublisher;

/**
 * 操作日志使用spring event异步入库
 *
 * @author zhaobohao
 */
@Slf4j
@Aspect
public class UsualLogAspect {

	@Around("@annotation(usualLog)")
	public Object around(ProceedingJoinPoint point, UsualLog usualLog) throws Throwable {
		//获取类名
		String className = point.getTarget().getClass().getName();
		//获取方法
		String methodName = point.getSignature().getName();
		// 发送异步日志事件
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//记录日志
		UsualLogPublisher.publishEvent(usualLog.level(),usualLog.value(),result.toString());
		return result;
	}

}
