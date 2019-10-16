package com.gitee.sop.adminserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final String REDIRECT_INDEX = "redirect:index.html";

    @GetMapping("/")
    public String index() {
        return REDIRECT_INDEX;
    }

}
