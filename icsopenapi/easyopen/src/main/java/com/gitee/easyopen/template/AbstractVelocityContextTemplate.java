package com.gitee.easyopen.template;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.support.VelocityContextHandler;
import com.gitee.easyopen.util.VelocityUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tanghc
 */
public abstract class AbstractVelocityContextTemplate extends AbstractTemplate {

    protected VelocityContextHandler velocityContextHandler;

    public AbstractVelocityContextTemplate(ApiConfig apiConfig, VelocityContextHandler velocityContextHandler) {
        super(apiConfig);
        this.velocityContextHandler = velocityContextHandler;
    }

    protected VelocityContext buildVelocityContext(HttpServletRequest request) {
        VelocityContext context = new VelocityContext();
        String requestUrl = request.getRequestURL().toString();
        context.put("url", requestUrl);
        context.put("ctx", request.getContextPath());
        context.put("loginUrl", getLoginUrl(requestUrl));
        return context;
    }


    protected String getLoginUrl(String requestUrl) {
        return requestUrl.substring(requestUrl.lastIndexOf("/") + 1);
    }

    protected boolean checkPassword(HttpServletRequest request, String serverPwd) {
        String inputPassword = request.getParameter("p");
        if (inputPassword == null) {
            return false;
        }
        if (serverPwd == null) {
            return false;
        }
        return DigestUtils.md5Hex(serverPwd).equals(inputPassword);
    }

    protected void toHtml(VelocityContext context, ClassPathResource res, HttpServletResponse response) throws IOException {
        VelocityUtil.generate(context, res.getInputStream(), response.getWriter());
    }
}
