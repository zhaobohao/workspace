package org.springclouddev.core.log.event;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.launch.props.SystemProperties;
import org.springclouddev.core.launch.server.ServerInfo;
import org.springclouddev.core.log.constant.EventConstant;
import org.springclouddev.core.log.feign.ILogClient;
import org.springclouddev.core.log.model.LogUsual;
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
public class UsualLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final SystemProperties systemProperties;

	@Async
	@Order
	@EventListener(UsualLogEvent.class)
	public void saveUsualLog(UsualLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogUsual logUsual = (LogUsual) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logUsual, systemProperties, serverInfo);
		logService.saveUsualLog(logUsual);
	}

}
