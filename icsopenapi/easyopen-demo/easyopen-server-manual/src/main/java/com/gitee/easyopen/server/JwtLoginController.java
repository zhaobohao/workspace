package com.gitee.easyopen.server;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.server.model.User;

@Controller
@ApiDoc("jwt")
public class JwtLoginController {

    
    @RequestMapping("jwtLogin")
    @ResponseBody
    @ApiDocMethod(description = "jwt登录示例")
    public String jwtLogin(HttpServletRequest request) {
        // 假设登录成功
        User user = new User();
        user.setId(22L);
        user.setUsername("jim");
        
        Map<String, String> data = new HashMap<>();
        data.put("id", user.getId().toString());
        data.put("username", user.getUsername());
        
        return ApiContext.createJwt(data);
    }
   
}
