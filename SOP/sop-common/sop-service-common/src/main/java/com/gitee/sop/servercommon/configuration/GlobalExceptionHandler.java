package com.gitee.sop.servercommon.configuration;

import com.gitee.sop.servercommon.bean.OpenContext;
import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.exception.ServiceException;
import com.gitee.sop.servercommon.result.ServiceResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 全局异常处理
 *
 * @author tanghc
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 与网关约定好的状态码，表示业务出错
     */
    private static final int BIZ_ERROR_CODE = 4000;

    /**
     * 与网关约定好的系统错误状态码
     */
    private static final int SYSTEM_ERROR_CODE = 5050;

    /**
     * 返回错误信息headerName
     */
    private static final String X_SERVICE_ERROR_HEADER_NAME = "x-service-error-code";

    /**
     * 捕获手动抛出的异常
     *
     * @param request   request
     * @param response  response
     * @param exception 异常信息
     * @return 返回提示信息
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Object serviceExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        response.addHeader(X_SERVICE_ERROR_HEADER_NAME, String.valueOf(BIZ_ERROR_CODE));
        return this.processError(request, response, exception);
    }

    /**
     * 捕获未知异常
     *
     * @param request   request
     * @param response  response
     * @param exception 异常信息
     * @return 返回提示信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        response.addHeader(X_SERVICE_ERROR_HEADER_NAME, String.valueOf(SYSTEM_ERROR_CODE));
        OpenContext openContext = ServiceContext.getCurrentContext().getOpenContext();
        log.error("系统错误, openContext:{}", openContext == null ? "null" : openContext, exception);
        StringBuilder msg = new StringBuilder();
        msg.append(exception.getMessage());
        StackTraceElement[] stackTrace = exception.getStackTrace();
        // 取5行错误内容
        int lineCount = 5;
        for (int i = 0; i < stackTrace.length && i < lineCount; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            msg.append("<br> at ").append(stackTraceElement.toString());
        }
        response.setHeader("x-service-error-message", UriUtils.encode(msg.toString(), StandardCharsets.UTF_8));
        return this.processError(request, response, new ServiceException("系统繁忙"));
    }

    /**
     * 处理异常
     *
     * @param request   request
     * @param response  response
     * @param exception 异常信息
     * @return 返回最终结果
     */
    protected Object processError(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        ServiceResultBuilder serviceResultBuilder = ServiceConfig.getInstance().getServiceResultBuilder();
        return serviceResultBuilder.buildError(request, response, exception);
    }
}
