package org.springclouddev.core.log.error;

import org.springclouddev.core.tool.jackson.JsonUtil;
import org.springclouddev.core.tool.utils.Charsets;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 更改html请求异常为ajax
 *
 * @author zhaobohao
 */
public class ErrorController extends BasicErrorController {

	public ErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
		super(errorAttributes, errorProperties);
	}

	@Override
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		response.setStatus(status.value());
		response.setCharacterEncoding(Charsets.UTF_8_NAME);
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setObjectMapper(JsonUtil.getInstance());
		view.setContentType(MediaType.APPLICATION_JSON_VALUE);
		return new ModelAndView(view, body);
	}
}
