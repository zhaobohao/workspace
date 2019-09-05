

package org.springclouddev.core.log.publisher;

import org.springclouddev.core.log.constant.EventConstant;
import org.springclouddev.core.log.event.UsualLogEvent;
import org.springclouddev.core.log.model.LogUsual;
import org.springclouddev.core.log.utils.LogAbstractUtil;
import org.springclouddev.core.tool.utils.SpringUtil;
import org.springclouddev.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * BLADE日志信息事件发送
 *
 * @author zhaobohao
 */
public class UsualLogPublisher {

	public static void publishEvent(String level, String id, String data) {
		HttpServletRequest request = WebUtil.getRequest();
		LogUsual logUsual = new LogUsual();
		logUsual.setLogLevel(level);
		logUsual.setLogId(id);
		logUsual.setLogData(data);

		LogAbstractUtil.addRequestInfoToLog(request, logUsual);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logUsual);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new UsualLogEvent(event));
	}

}
