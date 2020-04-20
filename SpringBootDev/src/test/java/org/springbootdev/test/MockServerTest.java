package org.springbootdev.test;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.ClearType;

import static org.mockserver.model.HttpClassCallback.callback;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;

public class MockServerTest {
	public static void main(String[] args) {
		ClientAndServer server = new ClientAndServer(1080);
		server.when(
			request()
				.withMethod("GET")
				.withPath("/test")
				.withQueryStringParameters(
					param("p", "1")
				)
		).respond(
			new RandomResponse()
		);

		server.when(
			request()
				.withMethod("GET")
				.withPath("/test")
				.withQueryStringParameters(
					param("p", "2")
				)
		).respond(
			response()
				.withBody("test2")
		);
		server.clear(request()
			.withMethod("GET")
			.withPath("/test")
			.withQueryStringParameters(
				param("p", "2")
			), ClearType.ALL);
		server.when(
			request()
				.withMethod("GET")
				.withPath("/test")
				.withQueryStringParameters(
					param("p", "2")
				)
		).respond(
			response()
				.withBody("test3")
		);
	}
}
