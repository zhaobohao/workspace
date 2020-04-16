package org.springbootdev.modules.mockserver.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpError;
import org.mockserver.model.HttpForward;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springbootdev.modules.mockserver.entity.MockHttp;
import org.springbootdev.modules.mockserver.service.IMockHttpService;
import org.springbootdev.modules.mockserver.wrapper.MockWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpOverrideForwardedRequest.forwardOverriddenRequest;

/**
 * 在项目启动的时候，初始化mock-server
 */
@Component
@Slf4j
@Order(value = 10)
public class MockServerInit implements CommandLineRunner {
	@Resource
	IMockHttpService mockHttpService;
	@Autowired
	ServerProperties serverProperties;

	@Override
	public void run(String... args) throws Exception {
		MockServerClient mockClient = new MockServerClient("localhost", serverProperties.getPort());
		mockHttpService.list().forEach(mockHttp -> {
			compileMockInterface(mockHttp, mockClient);
		});
	}

	/**
	 * 编译成mock接口，并发布接口到mockserver
	 *
	 * @param mockHttp
	 * @param mockClient
	 */
	public static void compileMockInterface(MockHttp mockHttp, MockServerClient mockClient) {

		//通过mock-server来初始化所有的接口。
		//通过参数，构造匹配的请求
		HttpRequest request = MockWrapper.mockRequest(mockHttp);
		//通过参数构造返回mock对象HttpError，优先级最高。
		HttpError error = MockWrapper.mockError(mockHttp);

		if (null != error) {
			mockClient
				.when(
					request
				).error(error);
			return;
		}

		//通过参数构造返回mock对象HttpForward，优先级中。
		HttpForward httpForward = MockWrapper.mockForward(mockHttp);
		if (null != httpForward) {
			mockClient
				.when(
					request
				)
				.forward(
					httpForward
				);
			return;
		}

		HttpRequest forwardRequest = MockWrapper.mockForwarRequest(mockHttp);
		if (null != forwardRequest) {
			mockClient
				.when(
					request
				)
				.forward(
					forwardOverriddenRequest(
						forwardRequest
					).withDelay(TimeUnit.SECONDS, StrUtil.isNotBlank(mockHttp.getForwardDelay()) ? Long.valueOf(mockHttp.getForwardDelay()) : 0L)
				);
			return;
		}
		//通过参数构造返回mock对象Response，优先级最低。
		HttpResponse response = MockWrapper.mockHttpResponse(mockHttp);
		//最后，生成response对象，并发布mock接口
		mockClient
			.when(request)
			.respond(response);
	}
}
