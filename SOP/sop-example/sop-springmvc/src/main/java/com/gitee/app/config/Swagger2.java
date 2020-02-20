package com.gitee.app.config;

import com.gitee.sop.servercommon.swagger.SwaggerSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 开启文档，本地微服务文档地址：http://localhost:2222/doc.html
 * http://ip:port/v2/api-docs
 */
@EnableSwagger2
public class Swagger2 extends SwaggerSupport {
    @Override
    protected String getDocTitle() {
        return "MVC_API";
    }

    @Override
    protected boolean swaggerAccessProtected() {
        return false;
    }
}