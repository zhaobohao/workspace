

package com.spring.web.core.web.resource.web.controller;

import com.spring.web.config.SpringBootPlusProperties;
import com.spring.web.core.api.ApiResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 图片等文件资源访问控制器
 * /api/resource 访问路径 用于区分 文件访问虚拟目录映射 /resource
 * @author geekidea
 * @date 2019/8/20
 * @since 1.2.1-RELEASE
 */
@Slf4j
@Controller
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    /**
     * 访问图片
     */
    @GetMapping("/image/{imageFileName}")
    @ApiOperation(value = "访问图片",notes = "访问图片",response = ApiResult.class)
    public void getImage(@PathVariable(required = true) String imageFileName, HttpServletResponse response) throws Exception{
        log.info("imageFileName:{}",imageFileName);
        // 重定向到图片访问路径
        response.sendRedirect(springBootPlusProperties.getResourceAccessPath() + imageFileName);
    }

}
