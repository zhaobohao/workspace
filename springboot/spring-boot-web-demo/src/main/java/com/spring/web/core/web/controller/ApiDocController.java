

package com.spring.web.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * Api文档
 * </p>
 *
 * @author geekidea
 * @date 2018-11-08
 */
@Controller
@RequestMapping("/docs")
@ApiIgnore
public class ApiDocController extends BaseController {

    /**
    * swaggerUI
    */
    @GetMapping("")
    public String swaggerUI(){
        return "redirect:/swagger-ui.html";
    }

}
