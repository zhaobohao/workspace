

package com.spring.web.config;


import com.spring.web.core.web.filter.CrossDomainFilter;
import com.spring.web.core.web.filter.RequestPathFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;


/**
 * @author geekidea
 * @date 2018-11-08
 */
@Configuration
public class FilterConfig {


    @Bean
    public Filter crossDomainFilter(){
        return new CrossDomainFilter();
    }

    @Bean
    public Filter requestPathFilter(){
        return new RequestPathFilter();
    }

    @Bean
    @Order(1)
    public FilterRegistrationBean requestPathFilterBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestPathFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    @Order(2)
    public FilterRegistrationBean crossDomainFilterBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(crossDomainFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

}
