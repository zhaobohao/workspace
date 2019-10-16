package com.gitee.sop.servercommon.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author tanghc
 */
public abstract class SwaggerSupport {

    /**
     * 获取文档标题
     * @return 返回文档标题
     */
    protected abstract String getDocTitle();

    @Bean
    @Primary
    public DocumentationPluginsManagerExt documentationPluginsManagerExt() {
        return new DocumentationPluginsManagerExt();
    }

    @Bean
    public Docket createRestApi() {
        return getDocket();
    }

    protected Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public SwaggerSecurityFilter swaggerSecurityFilter() {
        return new SwaggerSecurityFilter(swaggerAccessProtected());
    }

    /**
     * swagger访问是否加密保护
     * @return
     */
    protected boolean swaggerAccessProtected() {
        return true;
    }

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(getDocTitle())
                .description("文档描述")
                .termsOfServiceUrl("文档")
                .version("1.0")
                .build();
    }



}