

package org.springbootdev.core.log.publisher;

import org.springbootdev.core.log.constant.EventConstant;
import org.springbootdev.core.log.event.ErrorLogEvent;
import org.springbootdev.core.log.model.LogError;
import org.springbootdev.core.log.utils.LogAbstractUtil;
import org.springbootdev.core.tool.utils.Exceptions;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.core.tool.utils.SpringUtil;
import org.springbootdev.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常信息事件发送
 *
 * @author zhaobohao
 */
public class ErrorLogPublisher {

	public static void publishEvent(Throwable error, String requestUri) {
		HttpServletRequest request = WebUtil.getRequest();
		LogError logError = new LogError();
		logError.setRequestUri(requestUri);
		if (Func.isNotEmpty(error)) {
			logError.setStackTrace(Exceptions.getStackTraceAsString(error));
			logError.setExceptionName(error.getClass().getName());
			logError.setMessage(error.getMessage());
			StackTraceElement[] elements = error.getStackTrace();
			if (Func.isNotEmpty(elements)) {
				StackTraceElement element = elements[0];
				logError.setMethodName(element.getMethodName());
				logError.setMethodClass(element.getClassName());
				logError.setFileName(element.getFileName());
				logError.setLineNumber(element.getLineNumber());
			}
		}
		LogAbstractUtil.addRequestInfoToLog(request, logError);

		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logError);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new ErrorLogEvent(event));
	}

}
