package org.springclouddev.test.mockserver;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Cookie;

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
                response()
                        .withCookie(new Cookie("cKey", "cValue"))
                        .withBody("test1")
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
    }
}
