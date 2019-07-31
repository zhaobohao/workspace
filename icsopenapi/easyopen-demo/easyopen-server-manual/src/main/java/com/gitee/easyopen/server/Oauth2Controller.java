package com.gitee.easyopen.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Oauth2Controller {

    // http://localhost:8080/go_oauth2
    @GetMapping("go_oauth2")
    public String go_oauth2() {
        return "oauth2index";
    }
    
    @GetMapping("oauth2login")
    public String oauth2login() {
        return "oauth2login";
    }
    
    @GetMapping("oauth2callback")
    public String callback(String code, Model model) {
        model.addAttribute("code", code);
        return "getAccessToken";
    }
    
    // http://localhost:8080/refreshToken
    @GetMapping("refreshToken")
    public String refreshToken(String code, Model model) {
        return "refreshToken";
    }
}
