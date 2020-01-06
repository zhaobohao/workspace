package com.gitee.sop.gateway.controller;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 传统web开发入口
 * @author tanghc
 */
@WebServlet(urlPatterns = "/rest/*")
public class RestServlet extends HttpServlet {

    private static final String EMPTY_VERSION = "";

    @Value("${zuul.servlet-path:/zuul}")
    private String path;

    @Value("${sop.restful.path:/rest}")
    private String restPath;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        int index = url.indexOf(restPath);
        // 取/rest的后面部分
        String path = url.substring(index + restPath.length());
        request.setAttribute(SopConstants.REDIRECT_METHOD_KEY, path);
        request.setAttribute(SopConstants.REDIRECT_VERSION_KEY, EMPTY_VERSION);
        request.getRequestDispatcher(this.path).forward(request, response);
    }

}