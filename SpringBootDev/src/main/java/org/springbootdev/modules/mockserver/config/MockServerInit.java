package org.springbootdev.modules.mockserver.config;

import org.apache.logging.log4j.core.config.Order;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpError;
import org.mockserver.model.HttpForward;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springbootdev.modules.mockserver.entity.MockHttp;
import org.springbootdev.modules.mockserver.service.IMockHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static org.mockserver.model.HttpError.error;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * 在项目启动的时候，初始化mock-server
 */
@Component
@Order(value=10)
public class MockServerInit implements CommandLineRunner {
	@Resource
	IMockHttpService mockHttpService;
	@Autowired
	ServerProperties serverProperties;
	@Override
	public void run(String... args) throws Exception {
		List<MockHttp>  mockHttpList=mockHttpService.list();
		mockHttpList.forEach(mockHttp -> {
			//通过mock-server来初始化所有的接口。
			//通过参数，构造匹配的请求
			HttpRequest request=request();
			request.withPath();
			request.withBody();

			//通过参数构造返回mock对象Response，优先级最低。
			HttpResponse response=response();
			response.withBody();
			response.withStatusCode();
			//通过参数构造返回mock对象HttpForward，优先级中。
			HttpForward forward= forward();

			//通过参数构造返回mock对象HttpError，优先级最高。
			HttpError error= error();


			new MockServerClient("localhost", serverProperties.getPort())
				.when(
					request
				)
				.respond(
					response
				);
		});
	}
