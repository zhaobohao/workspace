package com.gitee.sop.adminserver.interceptor;

import com.gitee.easyopen.ApiMeta;
import com.gitee.easyopen.interceptor.ApiInterceptorAdapter;
import com.gitee.sop.adminserver.common.AdminErrors;
import com.gitee.sop.adminserver.common.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，验证用户是否登录
 *
 * @author tanghc
 */
public class LoginInterceptor extends ApiInterceptorAdapter {

    public static final String PREFIX = "nologin.";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception {
        if (WebContext.getInstance().getLoginUser() != null) {
            return true;
        } else {
            throw AdminErrors.NO_LOGIN.getException();
        }
    }

    @Override
    public boolean match(ApiMeta apiMeta) {
        String name = apiMeta.getName();
        // 以‘nologin.’开头的接口不拦截
        if (name.startsWith(PREFIX)) {
            return false;
        } else {
            return true;
        }
    }

}
