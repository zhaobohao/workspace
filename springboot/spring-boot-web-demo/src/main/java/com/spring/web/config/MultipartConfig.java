

package com.spring.web.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上传配置
 * @author geekidea
 * @since 2018-11-08
 */
@Configuration
public class MultipartConfig {

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        //factory.setMaxFileSize("100MB"); //KB,MB
        factory.setMaxFileSize(DataSize.of(100, DataUnit.MEGABYTES));
        ///设置总上传数据总大小
//        factory.setMaxRequestSize("100MB");
        factory.setMaxRequestSize(DataSize.of(100,DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

}
