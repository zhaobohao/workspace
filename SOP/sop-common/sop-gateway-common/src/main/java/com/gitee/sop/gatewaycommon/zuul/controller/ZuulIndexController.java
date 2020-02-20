package com.gitee.sop.gatewaycommon.zuul.controller;

import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.util.RequestUtil;
import com.gitee.sop.gatewaycommon.zuul.ValidateService;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zuul网关入口
 *
 * @author tanghc
 */
@Slf4j
@Controller
public class ZuulIndexController {

    private static final String EMPTY_VERSION = "";

    @Autowired
    private ValidateService validateService;

    @Value("${zuul.servlet-path:/zuul}")
    private String path;

    @Value("${sop.restful.path:/rest}")
    private String restPath;

    /**
     * 验证回调，可自定义实现接口
     */
    private ValidateService.ValidateCallback callback = (currentContext -> {
        try {
            currentContext.getRequest().getRequestDispatcher(path).forward(currentContext.getRequest(), currentContext.getResponse());
        } catch (Exception e) {
            log.error("请求转发异常", e);
        }
    });

    /**
     * 网关入口
     *
     * @param request  request
     * @param response response
     */
    @RequestMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse response) {
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.setRequest(RequestUtil.wrapRequest(request));
        currentContext.setResponse(response);
        validateService.validate(currentContext, callback);
    }

    /**
     * restful入口
     *
     * @param request  request
     * @param response response
     */
    @RequestMapping("/rest/**")
    public void rest(HttpServletRequest request, HttpServletResponse response) {
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.setRequest(RequestUtil.wrapRequest(request));
        currentContext.setResponse(response);

        String url = request.getRequestURL().toString();
        int index = url.indexOf(restPath);
        // 取/rest的后面部分
        String path = url.substring(index + restPath.length());
        ApiParam apiParam = ApiParam.createRestfulApiParam(path);
        ZuulContext.setApiParam(apiParam);

        validateService.validate(currentContext, callback);
    }

}
