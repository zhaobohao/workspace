

package org.springbootdev.core.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springbootdev.core.launch.props.SystemProperties;
import org.springbootdev.core.launch.server.ServerInfo;
import org.springbootdev.core.log.constant.EventConstant;
import org.springbootdev.core.log.model.LogApi;
import org.springbootdev.core.log.utils.LogAbstractUtil;
import org.springbootdev.modules.system.service.ILogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;


/**
 * 异步监听日志事件
 *
 * @author merryChen
 */
@Slf4j
@AllArgsConstructor
public class ApiLogListener {

	private final ILogService logService;
	private final ServerInfo serverInfo;
	private final SystemProperties systemProperties;


	@Async
	@Order
	@EventListener(ApiLogEvent.class)
	public void saveApiLog(ApiLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogApi logApi = (LogApi) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logApi, systemProperties, serverInfo);
		logService.saveApiLog(logApi);
	}

}
