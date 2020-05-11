package org.springbootdev.modules.mockserver.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpError;
import org.mockserver.model.HttpForward;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springbootdev.modules.mockserver.callback.ReplaceBodyParamtersCallBack;
import org.springbootdev.modules.mockserver.entity.MockHttp;
import org.springbootdev.modules.mockserver.service.IMockHttpService;
import org.springbootdev.modules.mockserver.wrapper.MockWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
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
@Resource
RedisTemplate j2CacheRedisTemplate;
	@Override
	public void run(String... args) throws Exception {
		ClientAndServer mockClient =  GlobalMockServerClient.INSTANCE.getInstance();
		mockClient.when(HttpRequest.request().withPath("/hello")).respond(HttpResponse.response().withBody("hello world!"));
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
	public  void compileMockInterface(MockHttp mockHttp, ClientAndServer mockClient) {

		//通过mock-server来初始化所有的接口。
		//通过参数，构造匹配的请求
		Optional<HttpRequest> request = MockWrapper.mockRequest(mockHttp);
		//通过参数构造返回mock对象HttpError，优先级最高。
		Optional<HttpError> error = MockWrapper.mockError(mockHttp);

		if (error.isPresent()) {
			mockClient
				.when(
					request.get()
				).error(error.get());
			return;
		}

		//通过参数构造返回mock对象HttpForward，优先级中。
		Optional<HttpRequest> forwardRequest = MockWrapper.mockForwarRequest(mockHttp);
		if ( forwardRequest.isPresent()) {
			mockClient
				.when(
					request.get()
				)
				.forward(
					forwardOverriddenRequest(
						forwardRequest.get()
					).withDelay(TimeUnit.SECONDS, StrUtil.isNotBlank(mockHttp.getForwardDelay()) ? Long.valueOf(mockHttp.getForwardDelay()) : 0L)
				);
			return;
		}

		Optional<HttpForward> httpForward = MockWrapper.mockForward(mockHttp);
		if (httpForward.isPresent()) {
			mockClient
				.when(
					request.get()
				)
				.forward(
					httpForward.get()
				);
			return;
		}

		//通过参数构造返回mock对象Response，优先级最低。
		if(StrUtil.isNotBlank(mockHttp.getResponseBody()) && mockHttp.getResponseBody().indexOf("${")>0)
		{
			//有参数点位符，需要替换。
			mockClient
				.when(request.get())
				.respond(
					new ReplaceBodyParamtersCallBack(mockHttp)
				);
		}else {
			Optional<HttpResponse> response = MockWrapper.mockHttpResponse(mockHttp);
			//最后，生成response对象，并发布mock接口
			if (response.isPresent()) {
				mockClient
					.when(request.get())
					.respond(
						response.get()
					);
			}
		}
	}
}
