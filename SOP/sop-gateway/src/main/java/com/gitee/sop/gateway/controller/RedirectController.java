package com.gitee.sop.gateway.controller;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tanghc
 */
@Controller
public class RedirectController {

    @Value("${zuul.servlet-path:/zuul}")
    private String path;

    @RequestMapping("/{method}/{version}/")
    public void redirect(
            @PathVariable("method") String method
            , @PathVariable("version") String version
            , HttpServletRequest request
            , HttpServletResponse response
    ) throws ServletException, IOException {
        request.setAttribute(SopConstants.REDIRECT_METHOD_KEY, method);
        request.setAttribute(SopConstants.REDIRECT_VERSION_KEY, version);
        request.getRequestDispatcher(path).forward(request, response);
    }

    @RequestMapping("/")
    public void index(
             HttpServletRequest request
            , HttpServletResponse response
    ) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

}
