package com.gitee.sop.adminserver.api.system;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.session.SessionManager;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.adminserver.api.system.param.LoginForm;
import com.gitee.sop.adminserver.api.system.result.AdminUserInfoVO;
import com.gitee.sop.adminserver.common.AdminErrors;
import com.gitee.sop.adminserver.common.WebContext;
import com.gitee.sop.adminserver.entity.AdminUserInfo;
import com.gitee.sop.adminserver.mapper.AdminUserInfoMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("系统接口")
public class SystemApi {


    public static final int STATUS_FORBIDDEN = 2;
    @Autowired
    AdminUserInfoMapper adminUserInfoMapper;

    @Api(name = "nologin.admin.login")
    @ApiDocMethod(description = "用户登录")
    String adminLogin(LoginForm param) {
        String username = param.getUsername();
        String password = param.getPassword();
        password = DigestUtils.md5Hex(password);

        Query query = new Query()
                .eq("username", username)
                .eq("password", password);
        AdminUserInfo user = adminUserInfoMapper.getByQuery(query);

        if (user == null) {
            throw AdminErrors.ERROR_USERNAME_PWD.getException();
        } else {
            if (user.getStatus() == STATUS_FORBIDDEN) {
                throw AdminErrors.USER_FORBIDDEN.getException();
            }
            SessionManager sessionManager = ApiContext.getSessionManager();
            // 生成一个新session
            HttpSession session = sessionManager.getSession(null);
            WebContext.getInstance().setLoginUser(session, user);
            return session.getId();
        }
    }


    @Api(name = "admin.userinfo.get")
    @ApiDocMethod(description = "获取用户信息")
    AdminUserInfoVO getAdminUserInfo() {
        AdminUserInfo loginUser = WebContext.getInstance().getLoginUser();
        AdminUserInfoVO adminUserInfoVO = new AdminUserInfoVO();
        BeanUtils.copyProperties(loginUser, adminUserInfoVO);
        return adminUserInfoVO;
    }
}
