package com.gitee.sop.gatewaycommon.zuul.controller;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.zuul.ValidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        validateService.validate(request, response, callback);
    }

    /**
     * restful入口
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/rest/**")
    public void rest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        int index = url.indexOf(restPath);
        // 取/rest的后面部分
        String path = url.substring(index + restPath.length());
        request.setAttribute(SopConstants.REDIRECT_METHOD_KEY, path);
        request.setAttribute(SopConstants.REDIRECT_VERSION_KEY, EMPTY_VERSION);
        request.setAttribute(SopConstants.SOP_NOT_MERGE, true);
        request.getRequestDispatcher(this.path).forward(request, response);
    }

    @RequestMapping("/{method}/{version}/")
    public void redirect(
            @PathVariable("method") String method
            , @PathVariable("version") String version
            , HttpServletRequest request
            , HttpServletResponse response
    ) {
        request.setAttribute(SopConstants.REDIRECT_METHOD_KEY, method);
        request.setAttribute(SopConstants.REDIRECT_VERSION_KEY, version);
        validateService.validate(request, response, callback);
    }
}
