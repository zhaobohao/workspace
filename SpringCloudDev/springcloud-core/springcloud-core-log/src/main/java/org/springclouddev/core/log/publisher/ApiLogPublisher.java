package org.springclouddev.core.log.publisher;

import org.springclouddev.core.log.annotation.ApiLog;
import org.springclouddev.core.log.constant.EventConstant;
import org.springclouddev.core.log.event.ApiLogEvent;
import org.springclouddev.core.log.model.LogApi;
import org.springclouddev.core.log.utils.LogAbstractUtil;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.utils.SpringUtil;
import org.springclouddev.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 *
 * @author zhaobohao
 */
public class ApiLogPublisher {

	public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		LogApi logApi = new LogApi();
		logApi.setType(ToolConstant.LOG_NORMAL_TYPE);
		logApi.setTitle(apiLog.value());
		logApi.setTime(String.valueOf(time));
		logApi.setMethodClass(methodClass);
		logApi.setMethodName(methodName);

		LogAbstractUtil.addRequestInfoToLog(request, logApi);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logApi);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}
