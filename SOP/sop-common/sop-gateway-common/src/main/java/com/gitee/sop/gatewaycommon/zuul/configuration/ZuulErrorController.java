package com.gitee.sop.gatewaycommon.zuul.configuration;

import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.result.ResultExecutor;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理网关自身异常
 *
 * @author tanghc
 */
@Controller
@Slf4j
public class ZuulErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    /**
     * 错误最终会到这里来
     */
    @RequestMapping(ERROR_PATH)
    @ResponseBody
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
        return ERROR_PATH;
    }
}
