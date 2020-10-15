package com.dc3.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Gateway Controller
 *

 */
@Controller
public class GatewayController {

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

}
