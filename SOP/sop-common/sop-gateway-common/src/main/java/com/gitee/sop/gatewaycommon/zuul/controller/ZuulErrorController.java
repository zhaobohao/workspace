package com.gitee.sop.gatewaycommon.zuul.controller;

import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.result.ResultExecutor;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zuul的异常处理
 *
 * @author tanghc
 */
@Slf4j
@RestController
public class ZuulErrorController implements ErrorController {

    /**
     * 错误最终会到这里来
     */
    @RequestMapping("/error")
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getResponse() == null) {
            ctx.setResponse(response);
        }
        Throwable throwable = ctx.getThrowable();
        log.error("zuul网关报错，URL:{}, status:{}, params:{}",
                request.getRequestURL().toString()
                , response.getStatus()
                , ZuulContext.getApiParam()
                , throwable);
        return this.buildResult(throwable);
    }

    protected Object buildResult(Throwable throwable) {
        ResultExecutor<RequestContext, String> resultExecutor = ApiContext.getApiConfig().getZuulResultExecutor();
        return resultExecutor.buildErrorResult(RequestContext.getCurrentContext(), throwable);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
