

package org.springbootdev.core.log.publisher;

import org.springbootdev.core.log.annotation.ApiLog;
import org.springbootdev.core.log.constant.EventConstant;
import org.springbootdev.core.log.event.ApiLogEvent;
import org.springbootdev.core.log.model.LogApi;
import org.springbootdev.core.log.utils.LogAbstractUtil;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springbootdev.core.tool.utils.SpringUtil;
import org.springbootdev.core.tool.utils.WebUtil;

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
