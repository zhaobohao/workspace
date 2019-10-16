package com.gitee.easyopen.server.config;

import com.gitee.sop.servercommon.configuration.EasyopenDocSupportController;
import com.gitee.sop.servercommon.easyopen.EasyopenServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * @author tanghc
 */
@Configuration
public class SopConfig extends EasyopenServiceConfiguration {

    @Controller
    public static class SopDocController extends EasyopenDocSupportController {
        @Override
        public String getDocTitle() {
            return "商品API";
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置映射关系
        registry.addResourceHandler("/opendoc/**").addResourceLocations("classpath:/META-INF/resources/opendoc/");
    }
}
