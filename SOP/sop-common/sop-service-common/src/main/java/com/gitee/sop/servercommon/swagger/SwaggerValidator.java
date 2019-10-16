package com.gitee.sop.servercommon.swagger;

import com.gitee.sop.servercommon.util.OpenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author tanghc
 */
public class SwaggerValidator {

    private String secret = "b749a2ec000f4f29";

    private boolean swaggerAccessProtected = true;

    public SwaggerValidator(boolean swaggerAccessProtected) {
        this.swaggerAccessProtected = swaggerAccessProtected;
    }

    public SwaggerValidator() {
    }

    /**
     * swagger访问是否加密保护
     * @return
     */
    public boolean swaggerAccessProtected() {
        return swaggerAccessProtected;
    }

    public boolean validate(HttpServletRequest request) {
        return OpenUtil.validateSimpleSign(request, secret);
    }

    public void writeForbidden(HttpServletResponse response) throws IOException {
        response.setContentType("text/palin;charset=UTF-8");
        response.setStatus(403);
        PrintWriter printWriter = response.getWriter();
        printWriter.write("access forbidden");
        printWriter.flush();
    }
}
