package com.gitee.sop.storyweb.config;

import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.configuration.AlipayServiceConfiguration;
import com.gitee.sop.servercommon.swagger.SwaggerSupport;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 使用支付宝开放平台功能
 * @author tanghc
 */
@Configuration
public class OpenServiceConfig extends AlipayServiceConfiguration {

    static {
        ServiceConfig.getInstance().getI18nModules().add("i18n/isp/goods_error");
    }


    /**
     * 开启文档，本地微服务文档地址：http://localhost:2222/doc.html
     * http://ip:port/v2/api-docs
     */
    @Configuration
    @EnableSwagger2
    public static class Swagger2 extends SwaggerSupport {
        @Override
        protected String getDocTitle() {
            return "故事API";
        }

        @Override
        protected boolean swaggerAccessProtected() {
            return false;
        }
    }

}

/**
 * 使用淘宝开放平台功能
 * @author tanghc
 */
//@Configuration
//public class OpenServiceConfig extends TaobaoServiceConfiguration {
//
//}
