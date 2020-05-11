package org.springclouddev.mockserver.wrapper;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.mockserver.matchers.MatchType;
import org.mockserver.model.*;
import org.springclouddev.mockserver.entity.MockHttp;
import org.springframework.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpError.error;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.RegexBody.regex;

/**
 * mock接口报文的wrapper类
 */
public class MockWrapper {

    /**
     * @param mockHttp
     * @return
     */
    public static Optional<HttpResponse> mockHttpResponse(MockHttp mockHttp) {
        //通过参数构造返回mock对象Response，优先级最低。
        HttpResponse response = response();
        if (StrUtil.isNotBlank(mockHttp.getResponseBody())) {
            if (StrUtil.isNotBlank(mockHttp.getResponseCharsets())) {
                try {
                    response.withBody(mockHttp.getResponseBody().getBytes(mockHttp.getResponseCharsets()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                response.withBody(mockHttp.getResponseBody());
            }
        }
        if (mockHttp.getResponseStatusCode() > 0) {
            response.withStatusCode(mockHttp.getResponseStatusCode());
            if (StrUtil.isNotBlank(mockHttp.getResponseReasonPhrase())) {
                response.withReasonPhrase(mockHttp.getResponseReasonPhrase());
            }
        }
        if (mockHttp.getResponseDelay() > 0)
            response.withDelay(TimeUnit.SECONDS, mockHttp.getResponseDelay());
        if (StrUtil.isNotBlank(mockHttp.getResponseHeaders())) {
            JSONObject jsonObject = JSONUtil.parseObj(mockHttp.getResponseHeaders());
            if (jsonObject.containsKey(HttpHeaders.CONTENT_TYPE)) {
                response.withContentType(MediaType.parse((String) jsonObject.get(HttpHeaders.CONTENT_TYPE)));
                jsonObject.remove(HttpHeaders.CONTENT_TYPE);
            }
            if (jsonObject.size() > 0) {
                Headers headers = new Headers();
                jsonObject.forEach((key, value) -> {
                            headers.withEntry(key, (String) value);
                        }
                );
                response.withHeaders(headers);
            }
        }
        return Optional.ofNullable(response);
    }

    /**
     * @param mockHttp
     * @return
     */
    public static Optional<HttpRequest> mockForwarRequest(MockHttp mockHttp) {
        HttpRequest forwardRequest = null;
        if (StrUtil.isNotBlank(mockHttp.getForwardPath())) {
            forwardRequest = new HttpRequest();
            forwardRequest.withPath(mockHttp.getForwardPath());
            if (StrUtil.isNotBlank(mockHttp.getForwardHeaders())) {
                JSONObject jsonObject = JSONUtil.parseObj(mockHttp.getForwardHeaders());
                if (jsonObject.containsKey(HttpHeaders.CONTENT_TYPE)) {
                    forwardRequest.withContentType(MediaType.parse((String) jsonObject.get(HttpHeaders.CONTENT_TYPE)));
                    jsonObject.remove(HttpHeaders.CONTENT_TYPE);
                }
                if (jsonObject.size() > 0) {
                    Headers headers = new Headers();
                    jsonObject.forEach((key, value) -> {
                                headers.withEntry(key, (String) value);
                            }
                    );
                    forwardRequest.withHeaders(headers);
                }
            }
            if (StrUtil.isNotBlank(mockHttp.getForwardBody())) {
                forwardRequest.withBody(mockHttp.getForwardBody());
            }
            if (StrUtil.isNotBlank(mockHttp.getForwardSocketAddress())) {
                JSONObject jsonObject = JSONUtil.parseObj(mockHttp.getForwardHeaders());
                forwardRequest.withSocketAddress((String) jsonObject.get("host"), (int) jsonObject.get("port"), "http".equals(jsonObject.get("scheme")) ? SocketAddress.Scheme.HTTP : SocketAddress.Scheme.HTTPS);
            }

        }
        return Optional.ofNullable(forwardRequest);
    }

    /**
     * @param mockHttp
     * @return
     */
    public static Optional<HttpForward> mockForward(MockHttp mockHttp) {
        //通过参数构造返回mock对象HttpForward，优先级中。
        HttpForward httpForward = null;
        if (StrUtil.isNotBlank(mockHttp.getForwardHost())) {
            httpForward = forward();
            httpForward.withHost(mockHttp.getForwardHost());

            if (StrUtil.isNotBlank(mockHttp.getForwardPort())) {
                httpForward.withPort(Integer.valueOf(mockHttp.getForwardPort()));
            }
            if (StrUtil.isNotBlank(mockHttp.getForwardDelay())) {
                httpForward.withDelay(TimeUnit.SECONDS, Long.valueOf(mockHttp.getForwardDelay()));
            }
        }
        return Optional.ofNullable(httpForward);
    }

    /**
     * 构造error对象
     *
     * @param mockHttp
     * @return
     */
    public static Optional<HttpError> mockError(MockHttp mockHttp) {
        //通过参数构造返回mock对象HttpError，优先级最高。
        HttpError error = null;
        if (StrUtil.isNotBlank(mockHttp.getErrorResponseBytes()) || StrUtil.isNotBlank(mockHttp.getErrorDropConnection())) {
            error = error();
            error.withDropConnection(StrUtil.isBlank(mockHttp.getErrorDropConnection()) ? Boolean.FALSE : Boolean.valueOf(mockHttp.getErrorDropConnection())).withResponseBytes(StrUtil.isNotBlank(mockHttp.getErrorResponseBytes()) ? mockHttp.getErrorResponseBytes().getBytes() : String.valueOf("").getBytes());
        }
        return Optional.ofNullable(error);
    }

    /**
     * mock当前使用的request对象
     *
     * @param mockHttp
     * @return
     */
    public static Optional<HttpRequest> mockRequest(MockHttp mockHttp) {
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
        if (StrUtil.isNotBlank(mockHttp.getRequestStringBody())) {
            request.withBody(regex(mockHttp.getRequestStringBody()));
        } else if (StrUtil.isNotBlank(mockHttp.getRequestJsonBody())) {
            request.withBody(json(mockHttp.getRequestJsonBody(), MatchType.ONLY_MATCHING_FIELDS)).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF_8.toString());
        } else if (StrUtil.isNotBlank(mockHttp.getRequestFormBody())) {
            request.withHeaders(
                    Header.header(HttpHeaders.CONTENT_TYPE, MediaType.FORM_DATA.toString())
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
        // 把headers放到最后，这样可以覆盖前面的默认设置
        if (StrUtil.isNotBlank(mockHttp.getRequestHeaders())) {
            JSONObject jsonObject = JSONUtil.parseObj(mockHttp.getRequestHeaders());
            if (jsonObject.containsKey(HttpHeaders.CONTENT_TYPE)) {
                if (jsonObject.get(HttpHeaders.CONTENT_TYPE) instanceof JSONArray)
                    request.withContentType(MediaType.parse((((JSONArray) jsonObject.get(HttpHeaders.CONTENT_TYPE)).toString())));
                else
                    request.withContentType(MediaType.parse((String) jsonObject.get(HttpHeaders.CONTENT_TYPE)));
                jsonObject.remove(HttpHeaders.CONTENT_TYPE);
            }
            if (jsonObject.size() > 0) {
                Headers headers = new Headers();
                jsonObject.forEach((key, value) -> {
                            if (jsonObject.get(HttpHeaders.CONTENT_TYPE) instanceof JSONArray) {
                                headers.withEntry(key, value.toString());
                            } else {
                                headers.withEntry(key, (String) value);
                            }
                        }
                );
                request.withHeaders(headers);
            }
        }
        return Optional.ofNullable(request);
    }

}
