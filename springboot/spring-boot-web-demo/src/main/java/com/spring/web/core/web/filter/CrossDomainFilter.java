

package com.spring.web.core.web.filter;

import com.alibaba.fastjson.JSON;
import com.spring.web.core.api.ApiResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 设置允许跨域
 * @author zhaobohao
 * @date 2018-11-08
 */
@Slf4j
public class CrossDomainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setHeader("Access-Control-Request-Headers","*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "content-type,x-auth-token");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "*");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String method = request.getMethod();
        if ("OPTIONS".equals(method)){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setCharacterEncoding("UTF-8");
            PrintWriter w = response.getWriter();
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("ok...");
            w.append(JSON.toJSONString(apiResult));
            return;
        }

        filterChain.doFilter(servletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {

    }
}
