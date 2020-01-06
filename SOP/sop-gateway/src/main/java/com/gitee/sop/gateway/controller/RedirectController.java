package com.gitee.sop.gateway.controller;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.zuul.ValidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 网关入口
 *
 * @author tanghc
 */
@Slf4j
@Controller
public class RedirectController {

    @Autowired
    private ValidateService validateService;

    @Value("${zuul.servlet-path:/zuul}")
    private String path;

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