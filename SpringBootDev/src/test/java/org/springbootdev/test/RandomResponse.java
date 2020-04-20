package org.springbootdev.test;

import cn.hutool.core.util.RandomUtil;
import org.mockserver.mock.action.ExpectationResponseCallback;
import org.mockserver.model.Cookie;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import static org.mockserver.model.HttpResponse.response;

public class RandomResponse
	implements ExpectationResponseCallback {
	@Override
	public HttpResponse handle(HttpRequest httpRequest) throws Exception {
		return response().withBody(RandomUtil.randomString(10)).withCookie(new Cookie("p", httpRequest.getFirstQueryStringParameter("p")));
	}


}
