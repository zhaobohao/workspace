package org.springbootdev.modules.mockserver.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.mockserver.client.MockServerClient;
import org.mockserver.matchers.MatchType;
import org.mockserver.model.*;
import org.springbootdev.modules.mockserver.entity.MockHttp;
import org.springbootdev.modules.mockserver.service.IMockHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static org.mockserver.model.HttpError.error;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

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
		List<MockHttp> mockHttpList = mockHttpService.list();
		mockHttpList.forEach(mockHttp -> {
			//通过mock-server来初始化所有的接口。
			//通过参数，构造匹配的请求
			HttpRequest request = request();
			if (StrUtil.isNotBlank(mockHttp.getRequestPath()))
				request.withPath(mockHttp.getRequestPath());
			if (StrUtil.isNotBlank(mockHttp.getRequestMethod()))
				request.withMethod(mockHttp.getRequestMethod());

			if (StrUtil.isNotBlank(mockHttp.getRequestCookies())) {
				JSONObject jsonObject = JSONUtil.parseObj(mockHttp.getRequestCookies());
				if (jsonObject.size() > 0) {
					Cookies cookies = new Cookies();
					jsonObject.forEach((key, value) -> {
						cookies.withEntry(key, (String) value);
					});
					request.withCookies(cookies);
				}
			}
			if (StrUtil.isNotBlank(mockHttp.getRequestParams())) {
				JSONObject jsonObject = JSONUtil.parseObj(mockHttp.getRequestParams());
				if (jsonObject.size() > 0) {
					Parameters parameters = new Parameters();
					jsonObject.forEach((key, value) -> {
						parameters.withEntry(key, (String) value);
					});
					request.withQueryStringParameters(parameters);
				}
			}
			if (StrUtil.isNotBlank(mockHttp.getRequestFormBody())) {
				request.withHeaders(
					Header.header(HttpHeaders.CONTENT_TYPE,MediaType.FORM_DATA.toString())
				);
				JSONObject jsonObject = JSONUtil.parseObj(mockHttp.getRequestFormBody());
				if (jsonObject.size() > 0) {
					Parameters parameters = new Parameters();
					jsonObject.forEach((key, value) -> {
						parameters.withEntry(key, (String) value);
					});
					request.withBody(ParameterBody.params(parameters.getEntries()));
				}
			}
			if (StrUtil.isNotBlank(mockHttp.getRequestJsonBody())) {
				request.withBody(json(mockHttp.getRequestJsonBody(), MatchType.ONLY_MATCHING_FIELDS)).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
			}
			// 把headers放到最后，这样可以覆盖前面的默认设置
			if (StrUtil.isNotBlank(mockHttp.getRequestHeaders())) {
				JSONObject jsonObject = JSONUtil.parseObj(mockHttp.getForwardHeaders());
				if (jsonObject.containsKey(HttpHeaders.CONTENT_TYPE)) {
					request.withContentType(MediaType.parse((String) jsonObject.get(HttpHeaders.CONTENT_TYPE)));
					jsonObject.remove(HttpHeaders.CONTENT_TYPE);
				}
				if (jsonObject.size() > 0) {
					Headers headers = new Headers();
					jsonObject.forEach((key, value) -> {
							headers.withEntry(key, (String) value);
						}
					);
					request.withHeaders(headers);
				}
			}

			//通过参数构造返回mock对象Response，优先级最低。
			HttpResponse response = response();
			response.withBody();
			response.withStatusCode();


			//通过参数构造返回mock对象HttpError，优先级最高。
			HttpError error = error();
			if(StrUtil.isNotBlank(mockHttp.getErrorResponseBytes()) || StrUtil.isNotBlank(mockHttp.getErrorDropConnection()))
			{
				new MockServerClient("localhost", serverProperties.getPort())
					.when(
						request
					).error(error().withDropConnection(StrUtil.isBlank(mockHttp.getErrorDropConnection())?Boolean.FALSE:Boolean.valueOf(mockHttp.getErrorDropConnection())).withResponseBytes(StrUtil.isNotBlank(mockHttp.getErrorResponseBytes())?mockHttp.getErrorResponseBytes().getBytes():String.valueOf("").getBytes()));
				return;
			}

//通过参数构造返回mock对象HttpForward，优先级中。
			HttpForward forward = forward();
			if(StrUtil.isNotBlank(mockHttp.getForwardPath()))
			{
				HttpForward httpForward=forward();
				httpForward.wi

			}
			new MockServerClient("localhost", serverProperties.getPort())
				.when(
					request
				)
				.respond(
					response
				);
		});
	}
