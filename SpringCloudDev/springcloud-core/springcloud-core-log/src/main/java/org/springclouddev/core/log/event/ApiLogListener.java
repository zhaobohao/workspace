package org.springclouddev.core.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.launch.props.SystemProperties;
import org.springclouddev.core.launch.server.ServerInfo;
import org.springclouddev.core.log.constant.EventConstant;
import org.springclouddev.core.log.feign.ILogClient;
import org.springclouddev.core.log.model.LogApi;
import org.springclouddev.core.log.utils.LogAbstractUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;


/**
 * 异步监听日志事件
 *
 * @author zhaobohao
 */
@Slf4j
@AllArgsConstructor
public class ApiLogListener {

	private final ILogClient logService;
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
