

package org.springbootdev.core.log.publisher;

import org.springbootdev.core.log.constant.EventConstant;
import org.springbootdev.core.log.event.UsualLogEvent;
import org.springbootdev.core.log.model.LogUsual;
import org.springbootdev.core.log.utils.LogAbstractUtil;
import org.springbootdev.core.tool.utils.SpringUtil;
import org.springbootdev.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志信息事件发送
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
