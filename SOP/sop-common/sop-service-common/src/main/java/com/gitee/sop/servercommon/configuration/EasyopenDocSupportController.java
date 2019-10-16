package com.gitee.sop.servercommon.configuration;

import com.gitee.easyopen.doc.ApiDocHolder;
import com.gitee.sop.servercommon.swagger.SwaggerValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档支持
 * @author tanghc
 */
public abstract class EasyopenDocSupportController {

    private SwaggerValidator swaggerValidator;

    /**
     * 获取文档标题
     * @return 返回文档标题
     */
    public abstract String getDocTitle();

    public EasyopenDocSupportController() {
        swaggerValidator = new SwaggerValidator(this.swaggerAccessProtected());
    }

    @RequestMapping("/v2/api-docs")
    @ResponseBody
    public Map<String, Object> getDocInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (swaggerValidator.swaggerAccessProtected() && !swaggerValidator.validate(request)) {
            swaggerValidator.writeForbidden(response);
            return null;
        }
        Map<String, Object> context = this.getContext();
        context.put("easyopen", "1.16.3");
        context.put("apiModules", ApiDocHolder.getApiDocBuilder().getApiModules());
        context.put("title", getDocTitle());
        return context;
    }

    public Map<String, Object> getContext() {
        return new HashMap<>(8);
    }

    /**
     * swagger访问是否加密保护
     * @return
     */
    protected boolean swaggerAccessProtected() {
        return true;
    }

}