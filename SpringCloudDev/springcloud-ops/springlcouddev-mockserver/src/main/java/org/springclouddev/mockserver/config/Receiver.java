package org.springclouddev.mockserver.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.ClearType;
import org.springclouddev.mockserver.entity.MockHttp;
import org.springclouddev.mockserver.service.IMockHttpService;
import org.springclouddev.mockserver.wrapper.MockWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhaobo
 *
 * 消息监听者
 **/
@Component
@Slf4j
public class Receiver {
	@Resource
	private IMockHttpService mockHttpService;
	@Resource
	private MockServerInit  mockServerInit;

	public void receiveMessage(String mockHttpID) {
		log.info("receive mockserver change message：{}", mockHttpID);

		if(StrUtil.isBlank(mockHttpID))
		{
			log.error("receive bad message .message is {}",mockHttpID);
			return;
		}
		ClientAndServer mockClient = GlobalMockServerClient.INSTANCE.getInstance();
		//收到mockserver接口变动的消息后，开始处理接口变动信息
//清理之前的mock接口
		MockHttp mockHttp=this.mockHttpService.getById(mockHttpID);
		mockClient.clear(MockWrapper.mockRequest(mockHttp).get(), ClearType.ALL);
		//创建新的mock接口,用redis发布接口变动消息
		this.mockServerInit.compileMockInterface(mockHttp,mockClient);
		log.info("update mockserver {} completed!",mockHttpID);
	}
}
