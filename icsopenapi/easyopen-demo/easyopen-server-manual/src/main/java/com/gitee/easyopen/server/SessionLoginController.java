package com.gitee.easyopen.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.server.model.User;
import com.gitee.easyopen.session.SessionManager;

@RestController
public class SessionLoginController {
    
    @RequestMapping("sessionLogin")
    public String sessionLogin(HttpServletRequest request) {
        return request.getSession().getId();
    }

    // 自定义session
    @PostMapping("managedSessionLogin")
    public String managedSessionLogin(HttpServletRequest request) {
        // 假设登陆成功,创建一个sessionId返回给客户端
        SessionManager sessionManager = ApiContext.getApiConfig().getSessionManager();
        HttpSession session = sessionManager.getSession(null);
        User user = new User(); // 存入session中的对象必须实现Serializable接口
        user.setId(1L);
        user.setUsername("tom");
        
        session.setAttribute("user", user);
        
        session.setAttribute("username", "tom");
        
        User userRedis = (User)session.getAttribute("user");
        System.out.println(JSON.toJSONString(userRedis));
        
        return session.getId();
    }

}
