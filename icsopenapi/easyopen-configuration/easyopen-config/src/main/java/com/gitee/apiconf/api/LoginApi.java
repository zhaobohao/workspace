package com.gitee.apiconf.api;

import com.gitee.apiconf.api.param.LoginForm;
import com.gitee.apiconf.common.ConfErrors;
import com.gitee.apiconf.common.WebContext;
import com.gitee.apiconf.entity.AdminUser;
import com.gitee.apiconf.mapper.AdminUserMapper;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.session.SessionManager;
import com.gitee.fastmybatis.core.query.Query;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@ApiService
public class LoginApi {
    @Autowired
    AdminUserMapper adminUserMapper;

    @Api(name = "nologin.admin.easyui.login")
    String adminLogin0(LoginForm param) {
        String username = param.getUsername();
        String password = param.getPassword();
        password = DigestUtils.md5Hex(password);

        Query query = new Query().eq("username", username).eq("password", password);
        AdminUser user = adminUserMapper.getByQuery(query);

        if (user == null) {
            throw ConfErrors.ERROR_USERNAME_PWD.getException();
        } else {
            SessionManager sessionManager = ApiContext.getSessionManager();
            // 生成一个新session
            HttpSession session = sessionManager.getSession(null);
            WebContext.getInstance().setAccessUser(session, user);
            return session.getId();
        }
    }

    // VUE登录
    @Api(name = "nologin.admin.login")
    String adminLogin(LoginForm param) {
        String username = param.getUsername();
        String password = param.getPassword();
        password = DigestUtils.md5Hex(password);

        Query query = new Query().eq("username", username).eq("password", password);
        AdminUser user = adminUserMapper.getByQuery(query);

        if (user == null) {
            throw ConfErrors.ERROR_USERNAME_PWD.getException();
        } else {
            Map<String, String> data = new HashMap<>();
            data.put("id", user.getId().toString());
            data.put("username", user.getUsername());

            String jwt = ApiContext.createJwt(data);
            System.out.println("jwt:" + jwt);
            WebContext.getInstance().setJwtUser(user);
            return jwt;
        }
    }


    @Api(name = "nologin.admin.logout")
    void adminLogout() {
        WebContext.getInstance().setAccessUser(ApiContext.getSession(),null);
    }
}
