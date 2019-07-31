package com.gitee.easyopen.template;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ParamNames;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.doc.ApiDocBuilder;
import com.gitee.easyopen.doc.ApiDocHolder;
import com.gitee.easyopen.support.VelocityContextHandler;
import com.gitee.easyopen.util.PDFUtil;
import com.gitee.easyopen.util.VelocityUtil;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tanghc
 */
public class DocTemplate extends AbstractVelocityContextTemplate {
    private static final String SESSION_KEY_DOC_PASSWORD = "session_key_doc_password";

    public DocTemplate(ApiConfig apiConfig, VelocityContextHandler velocityContextHandler) {
        super(apiConfig, velocityContextHandler);
    }

    public void processDoc(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        if (!this.apiConfig.isShowDoc()) {
            response.getWriter().write("文档功能未开启");
            return;
        }

        Object value = request.getSession().getAttribute(SESSION_KEY_DOC_PASSWORD);
        VelocityContext context = this.buildVelocityContext(request);
        String requestUrl = request.getRequestURL().toString();
        context.put("apiUrl", requestUrl.subSequence(0, requestUrl.length() - 4));
        // 如果设置了密码，且没有登陆
        if (StringUtils.hasText(this.apiConfig.getDocPassword()) && value == null) {
            // 校验登陆密码
            if (this.checkDocPassword(request)) {
                request.getSession().setAttribute(SESSION_KEY_DOC_PASSWORD, true);
                response.sendRedirect(String.valueOf(context.get("loginUrl")));
                return;
            } else {
                ClassPathResource res = new ClassPathResource(this.apiConfig.getLoginClassPath());
                context.put("title", "API文档");
                context.put("remark", "文档页面密码：");

                VelocityUtil.generate(context, res.getInputStream(), response.getWriter());
                return;
            }
        } else { // 没有设置密码或已经登录
            ClassPathResource res = new ClassPathResource(this.apiConfig.getDocClassPath());

            context.put("title", "API文档");
            ApiDocBuilder docBuilder = ApiDocHolder.getApiDocBuilder();
            context.put("apiModules", docBuilder.getApiModules());

            context.put("ACCESS_TOKEN_NAME", ParamNames.ACCESS_TOKEN_NAME);
            context.put("API_NAME", ParamNames.API_NAME);
            context.put("APP_KEY_NAME", ParamNames.APP_KEY_NAME);
            context.put("DATA_NAME", ParamNames.DATA_NAME);
            context.put("FORMAT_NAME", ParamNames.FORMAT_NAME);
            context.put("SIGN_METHOD_NAME", ParamNames.SIGN_METHOD_NAME);
            context.put("SIGN_NAME", ParamNames.SIGN_NAME);
            context.put("TIMESTAMP_NAME", ParamNames.TIMESTAMP_NAME);
            context.put("TIMESTAMP_PATTERN", ParamNames.TIMESTAMP_PATTERN);
            context.put("VERSION_NAME", ParamNames.VERSION_NAME);

            context.put("code_name", "code");
            context.put("code_description", "状态值，\"0\"表示成功，其它都是失败");

            context.put("msg_name", "msg");
            context.put("msg_description", "错误信息，出错时显示");

            context.put("data_name", "data");
            context.put("data_description", "返回的数据，没有则返回{}");

            context.put("docRemark", velocityContextHandler.getDocRemark());

            context.put("jsHook", "");
            context.put("sys_time", System.currentTimeMillis());
            // 文档左边树默认全部展开
            context.put("is_expand_all", true);

            this.processDocVelocityContext(context);

            this.toHtml(context, res, response);
        }
    }

    /**
     * 下载文档
     * @param request
     * @param response
     * @throws IOException
     */
    public void downloadPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!this.apiConfig.isShowDoc()) {
            response.getWriter().write("文档功能未开启");
            return;
        }
        Object value = request.getSession().getAttribute(SESSION_KEY_DOC_PASSWORD);
        VelocityContext context = this.buildVelocityContext(request);
        // 如果设置了密码，且没有登陆
        if (StringUtils.hasText(this.apiConfig.getDocPassword()) && value == null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            // 校验登陆密码
            if (this.checkDocPassword(request)) {
                request.getSession().setAttribute(SESSION_KEY_DOC_PASSWORD, true);
                response.sendRedirect(String.valueOf(context.get("loginUrl")));
            } else {

                ClassPathResource res = new ClassPathResource(this.apiConfig.getLoginClassPath());
                context.put("title", "API文档");
                context.put("remark", "文档页面密码：");

                VelocityUtil.generate(context, res.getInputStream(), response.getWriter());
            }
            return;
        }

        ClassPathResource res = new ClassPathResource(this.apiConfig.getDocPdfClassPath());
        ClassPathResource cssRes = new ClassPathResource(this.apiConfig.getDocPdfCssClassPath());

        context.put("title", "API文档");
        ApiDocBuilder docBuilder = ApiDocHolder.getApiDocBuilder();
        context.put("apiModules", docBuilder.getApiModules());
        context.put("ACCESS_TOKEN_NAME", ParamNames.ACCESS_TOKEN_NAME);
        context.put("API_NAME", ParamNames.API_NAME);
        context.put("APP_KEY_NAME", ParamNames.APP_KEY_NAME);
        context.put("DATA_NAME", ParamNames.DATA_NAME);
        context.put("FORMAT_NAME", ParamNames.FORMAT_NAME);
        context.put("SIGN_METHOD_NAME", ParamNames.SIGN_METHOD_NAME);
        context.put("SIGN_NAME", ParamNames.SIGN_NAME);
        context.put("TIMESTAMP_NAME", ParamNames.TIMESTAMP_NAME);
        context.put("TIMESTAMP_PATTERN", ParamNames.TIMESTAMP_PATTERN);
        context.put("VERSION_NAME", ParamNames.VERSION_NAME);
        context.put("code_name", "code");
        context.put("code_description", "状态值，\"0\"表示成功，其它都是失败");

        context.put("msg_name", "msg");
        context.put("msg_description", "错误信息，出错时显示");

        context.put("data_name", "data");
        context.put("data_description", "返回的数据，没有则返回{}");

        context.put("docRemark", velocityContextHandler.getDocRemark());
        context.put("fileName", "API文档" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf");
        this.processDocVelocityContext(context);

        String fileName = URLEncoder.encode((String) context.get("fileName"), "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("application/octet-stream;charset=UTF-8");

        String content = VelocityUtil.generateToString(context, res.getInputStream());
        String css = IOUtils.toString(cssRes.getInputStream(), Consts.UTF8);
        PDFUtil.htmlToPDF(content, css, response.getOutputStream(), (PDFUtil.PDFInfo) context.get("pdf_info"));
    }

    public boolean checkDocPassword(HttpServletRequest request) {
        return this.checkPassword(request, this.apiConfig.getDocPassword());
    }

    /**
     * 对doc模板做额外处理
     *
     * @param context
     */
    protected void processDocVelocityContext(VelocityContext context) {
        this.velocityContextHandler.processDocVelocityContext(context);
    }

}
