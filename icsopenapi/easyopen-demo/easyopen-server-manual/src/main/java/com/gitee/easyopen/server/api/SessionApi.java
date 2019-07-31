package com.gitee.easyopen.server.api;

import javax.servlet.http.HttpSession;

import com.gitee.easyopen.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;

/**
 * 业务类
 *
 * @author tanghc
 */
@ApiService
public class SessionApi {

    @Autowired
    UserService userService;

    private String key = "user";

    @Api(name = "session.get")
    public Object session() {
        HttpSession session = ApiContext.getSession();

        Object user = session.getAttribute(key);

        return user;
    }

    @Api(name = "session.set")
    public void setsession() {
        HttpSession session = ApiContext.getSession();

        session.setAttribute(key, userService.getUser());
    }

    @Api(name = "manager.session.get")
    public Object managersetsession() {
        HttpSession session = ApiContext.getManagedSession();

        System.out.println(session.getId());
        User user = (User)session.getAttribute("user");
        System.out.println(user);

        System.out.println("getCreationTime:" + session.getCreationTime());
        System.out.println("getMaxInactiveInterval:" + session.getMaxInactiveInterval());
        System.out.println("getLastAccessedTime:" + session.getLastAccessedTime());

        Object username = session.getAttribute("username");

        return username;
    }

}
